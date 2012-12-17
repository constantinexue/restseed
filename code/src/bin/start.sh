#!/bin/sh

if [ -f pid ]
then
	echo "PID file is existed, maybe program is running ..."
else
	java -jar ./lib/constantinexue-restseed-1.0.0.jar &
	echo $! > pid
fi

