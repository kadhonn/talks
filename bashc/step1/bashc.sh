#!/bin/bash

rootfs_dir="$1"
executable="$2"
host_port="$3"
container_port="$4"
host_path="$5"
container_path="$6"
port_forward=\''{"execute": "add_hostfwd", "arguments": {"proto": "tcp", "host_addr": "0.0.0.0", "host_port": '"$host_port"', "guest_addr": "10.0.2.100", "guest_port": '"$container_port"'}}'\'

#making a new pid namespace here to automatically cleanup slirp4netns when process is killed
unshare -Urmpf /bin/bash -c "

#prepare necessary mounts for networking
mount -t tmpfs netns /run/netns
mount -t tmpfs tmp /tmp

#create netns and configure slirp4netns with a portforward
ip netns add bashc
slirp4netns -c --netns-type=path /run/netns/bashc -a /tmp/slirpsocket tap0 &
sleep 1
echo -n $port_forward | nc -U /tmp/slirpsocket

#bind mount host dir into container
mkdir -p "${rootfs_dir}${container_path}"
mount --bind "$host_path" "${rootfs_dir}${container_path}"

#prepare proc dir and create resolv.conf so that dns works
mkdir -p "$rootfs_dir/proc"
echo 'nameserver 8.8.8.8' > resolv.conf

#drop capabilities, change network namespace, unshare the rest of the namespaces, remount /proc and chroot
capsh --drop=cap_sys_boot -- -c \
    'ip netns exec bashc unshare -muipTf --mount-proc '"--root=$rootfs_dir"' $executable'
"
