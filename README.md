for local test
```
curl -u guest:guest -H "Content-Type:application/json" -X POST -d '{"properties":{}, "routing_key":"test","payload":"{\"message\":\"hello!\"}","payload_encoding":"string"}' http://localhost:15672/api/exchanges/%2f/test_exchange/publish
```
