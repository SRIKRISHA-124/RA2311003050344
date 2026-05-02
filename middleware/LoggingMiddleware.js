const logToAPI = require("./logger");

const loggingMiddleware = async (req, res, next) => {

  // BEFORE request
  await logToAPI(
    "info",
    "api",
    `Incoming ${req.method} ${req.url} from ${req.ip}`
  );

  // AFTER response
  res.on("finish", async () => {
    await logToAPI(
      "info",
      "api",
      `Response status: ${res.statusCode}`
    );
  });

  next();
};

module.exports = loggingMiddleware;