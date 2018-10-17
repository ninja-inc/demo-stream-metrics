(1) when acknowledged
```
curl -u guest:guest -H "Content-Type:application/json" -X POST -d '{"properties":{}, "routing_key":"test","payload":"{\"message\":\"hello!\"}","payload_encoding":"string"}' http://localhost:15672/api/exchanges/%2f/test_exchange/publish
```
http://localhost:8080/actuator/metrics/spring.integration.send
```
{
	"name": "spring.integration.send",
	"description": "Send processing time",
	"baseUnit": "seconds",
	"measurements": [
		{
			"statistic": "COUNT",
			"value": 2
		},
		{
			"statistic": "TOTAL_TIME",
			"value": 0.126363987
		},
		{
			"statistic": "MAX",
			"value": 0.06333587
		}
	],
	"availableTags": [
		{
			"tag": "result",
			"values": [
				"success"
			]
		},
		{
			"tag": "exception",
			"values": [
				"none"
			]
		},
		{
			"tag": "name",
			"values": [
				"test_channel",
				"unknown"
			]
		},
		{
			"tag": "type",
			"values": [
				"channel",
				"handler"
			]
		}
	]
}
```


(2) when `AmqpRejectAndDontRequeueException` is thrown
```
curl -u guest:guest -H "Content-Type:application/json" -X POST -d '{"properties":{}, "routing_key":"test","payload":"{\"message\":\"hello!\", \"isDlq\": \"true\"}","payload_encoding":"string"}' http://localhost:15672/api/exchanges/%2f/test_exchange/publish
```
http://localhost:8080/actuator/metrics/spring.integration.send
```
{
	"name": "spring.integration.send",
	"description": "Send processing time",
	"baseUnit": "seconds",
	"measurements": [
		{
			"statistic": "COUNT",
			"value": 10
		},
		{
			"statistic": "TOTAL_TIME",
			"value": 0.166683403
		},
		{
			"statistic": "MAX",
			"value": 0.071057339
		}
	],
	"availableTags": [
		{
			"tag": "result",
			"values": [
				"success",
				"failure"
			]
		},
		{
			"tag": "exception",
			"values": [
				"MessagingException",
				"none",
				"ListenerExecutionFailedException"
			]
		},
		{
			"tag": "name",
			"values": [
				"test_channel",
				"test_exchange.anonymous.SGAcZ-6rShSkTfCKfFNAOg.errors.bridge",
				"errorChannel",
				"test_exchange.anonymous.SGAcZ-6rShSkTfCKfFNAOg.errors",
				"_org.springframework.integration.errorLogger",
				"unknown"
			]
		},
		{
			"tag": "type",
			"values": [
				"channel",
				"handler"
			]
		}
	]
}
```
