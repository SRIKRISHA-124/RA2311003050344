const express = require("express");
const loggingMiddleware = require("./middleware");

const app = express();

app.use(express.json());
app.use(loggingMiddleware);

// Sample APIs
app.get("/", (req, res) => {
  res.send("Home working");
});

app.get("/error", (req, res) => {
  throw new Error("Test error");
});

app.listen(3000, () => {
  console.log("Server running on port 3000");
});