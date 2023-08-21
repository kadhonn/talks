#!/bin/bash

#rootfs_dir="$1"
#host_port="$2"
#container_port="$3"
#host_path="$4"
#container_path="$5"
#port_forward=\''{"execute": "add_hostfwd", "arguments": {"proto": "tcp", "host_addr": "0.0.0.0", "host_port": '"$host_port"', "guest_addr": "10.0.2.100", "guest_port": '"$container_port"'}}'\'
config_file="$1"
envs="$(cat ${config_file} | jq -r '.process.env[]' | sed 's/\(.*\)=\(.*\)/"\1=\2"/' | sed -z 's/\n/ /g')"

eval env -i - $envs env
exit 0

unshare -Urmpf /bin/bash -c "
mount -t tmpfs netns /run/netns
mount -t tmpfs tmp /tmp
ip netns add bashrc

slirp4netns -c --netns-type=path /run/netns/bashrc -a /tmp/slirpsocket tap0 &
sleep 1
echo -n $port_forward | nc -U /tmp/slirpsocket

mkdir -p "$rootfs_dir/proc"
mkdir -p "$rootfs_dir/etc"
mkdir -p "${rootfs_dir}${container_path}"
mount --bind "$host_path" "${rootfs_dir}${container_path}"
echo 'nameserver 8.8.8.8' > resolv.conf
ip netns exec bashrc unshare -muipTf --mount-proc "--root=$rootfs_dir" /bin/sh
"




exit 0
#######################


items="
one two three four
hello world
this should work just fine
"

IFS='
'
count=0
for item in $items
do
  count=$((count+1))
  echo $count $item
done

