#!/bin/sh
if [ -f pid ]
then
	kill `cat pid` &
	wait $!
	rm pid
else
	echo "PID file is not existed, maybe program is stopped ..."
fi
