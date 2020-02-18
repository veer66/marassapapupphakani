#!/bin/sh

docker run --rm -it -u $(ls -n README.md | awk '{ print $3 }') \
       --net=host -v $(pwd):/work -e HOME=/work -w /work veer66/node-jdk \
       bash

