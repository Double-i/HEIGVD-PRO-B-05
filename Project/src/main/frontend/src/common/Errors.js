/**
 * Request error throw when a http request fail
 */
export class RequestError extends Error {
    constructor(message, errorCode, response) {
        super(message);
        this.errorCode = errorCode
        this.response = response
    }
}