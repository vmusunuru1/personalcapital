#i!/bin/sh

for i in {4..10}; do
  curl -XPOST https://vpc-estesting-4uq4wt56en2swspu5a33xtol3q.us-west-2.es.amazonaws.com/_bulk --data-binary @output${i}.json -H 'Content-Type: application/json'

   echo $i
done
