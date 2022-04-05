## 1a
- Which interaction style (synchrony, invocation semantics) is used in the communication between the test client and your Web service?
  - synchronous communication, RPC-style invocation
- Describe the activities of the system components during the communication process.
  - Webserver provides HTTP endpoint
  - Client gets data from the server as XML document

## 1b
Get the WSDL description of the Fibonacci Web Service using a Web browser (run TestWsServer.java, GET the Web service endpoint with query param 'wsdl') and describe its characteristics.
http://localhost:4434/fibonacciservice?wsdl


- What **kind of binding** was used?
  - SOAP HTTP Binding
- Which **communication style** was used for the specific binding?
  - synchronous communication via interface style
- What kind of **message encoding** was used?
  - both input and output use literal encoding (xml)

## Web server response
- [Server response](wsdl.xml)