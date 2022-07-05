#!/bin/bash
set -e
sh -c './wait-for-it.sh db:3306 -t 0'
exec "$@"