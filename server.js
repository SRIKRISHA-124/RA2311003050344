const express = require("express");
const loggingMiddleware = require("./middleware/LoggingMiddleware");

const app = express();

app.use(express.json());
app.use(loggingMiddleware);

// Test APIs
app.get("/", (req, res) => {
  res.send("Home working");
});

app.get("/error", (req, res) => {
  throw new Error("Test error");
});

// Error handler
app.use((err, req, res, next) => {
  res.status(500).send("Internal Server Error");
});

app.listen(3000, () => {
  console.log("Server running on port 3000");
});