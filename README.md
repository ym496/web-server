# minimal http server in java
This is a lightweight multi-threaded HTTP server built from scratch using Java. It can handle GET, POST, and other HTTP requests asynchronously. It supports HTTP methods and serves files from a specified directory. It can process multiple requests at the same time by leveraging Java’s threading mechanism. I created this to understand how HTTP servers work.

## demo
* serves files with different MIME type from a specified dir
<div align="center">
<img src="https://i.imgur.com/GfdiNUL.png" alt="web server demo" width="600" /> </centre>
</div>

* http method POST example
<div align="center">
<img src="https://i.imgur.com/UBxRx3v.png" alt="web server demo" width="600" /> </centre>
</div>

*  this server uses Java threads to handle client requests concurrently. Each client request is processed in its own thread, ensuring non-blocking, simultaneous processing of multiple requests.
## Installation

1. Clone this repo:
   ```bash
   git clone git@github.com:ym496/web-server.git
   cd web-server
   ```
2. Compile the server:
   ```bash
   javac Server.java
   ```
3. Run the server:
   ```bash
   java Server
   ```
## License

This project is licensed under the MIT License.
