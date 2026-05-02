const fetch = require("node-fetch");
const TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJNYXBDbGFpbXMiOnsiYXVkIjoiaHR0cDovLzIwLjI0NC41Ni4xNDQvZXZhbHVhdGlvbi1zZXJ2aWNlIiwiZW1haWwiOiJzYjE1MjVAc3JtaXN0LmVkdS5pbiIsImV4cCI6MTc3NzcwNzM5MSwiaWF0IjoxNzc3NzA2NDkxLCJpc3MiOiJBZmZvcmQgTWVkaWNhbCBUZWNobm9sb2dpZXMgUHJpdmF0ZSBMaW1pdGVkIiwianRpIjoiZjg5YTc3MjAtM2RmMy00N2ViLTljY2MtNWIzN2QxZTdmOGJlIiwibG9jYWxlIjoiZW4tSU4iLCJuYW1lIjoiYi5zcmlrcmlzaGEiLCJzdWIiOiIyNzdmNjNmYi0xYmM5LTQzOTctYmFjOS1iZmE0ODkzYjc0OTkifSwiZW1haWwiOiJzYjE1MjVAc3JtaXN0LmVkdS5pbiIsIm5hbWUiOiJiLnNyaWtyaXNoYSIsInJvbGxObyI6InJhMjMxMTAwMzA1MDM0NCIsImFjY2Vzc0NvZGUiOiJRa2JweEgiLCJjbGllbnRJRCI6IjI3N2Y2M2ZiLTFiYzktNDM5Ny1iYWM5LWJmYTQ4OTNiNzQ5OSIsImNsaWVudFNlY3JldCI6IlJaanN2V3F3R25ncUNTY2YifQ.jngFpc_g2xgj5D5ovybUlbYvkrUTzfudn4zAaRdCNA8";

const loggingMiddleware = (req, res, next) => {
  fetch("http://20.207.122.201/evaluation-service/logs", {
    method: "POST",
    headers: {
      Authorization: `Bearer ${TOKEN}`,
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      stack: "backend",
      level: "info",
      package: "api",
      message: `Incoming ${req.method} ${req.url} from ${req.ip}`,
    }),
  }).catch((err) => {
    console.log("Logging failed");
  });

  res.on("finish", () => {
    fetch("http://20.207.122.201/evaluation-service/logs", {
      method: "POST",
      headers: {
        Authorization: `Bearer ${TOKEN}`,
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        stack: "backend",
        level: "info",
        package: "api",
        message: `Response status: ${res.statusCode}`,
      }),
    }).catch((err) => {
      console.log("Response logging failed");
    });
  });

  next();
};

module.exports = loggingMiddleware;