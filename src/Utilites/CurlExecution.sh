#i!/bin/sh

for i in {1..229}; do
  curl -s -H "Content-Type: application/json"  vpc-searchelastic-orarpi2udhmbvoqwta5hrn743q.us-west-2.es.amazonaws.com/_bulk --data-binary @output${i}.json -H 'Content-Type: application/json'

   echo $i
done
