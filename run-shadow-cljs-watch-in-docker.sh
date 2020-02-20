#!/bin/sh
docker run \
       --rm \
       -it \
       -u $(ls -n README.md | awk '{ print $3 }') \
       --net=host \
       -v $(pwd):/work \
       -e HOME=/work \
       -e LANG=C.UTF-8 \
       -w /work \
       veer66/node-jdk \
       npx \
       shadow-cljs \
       watch \
       app
