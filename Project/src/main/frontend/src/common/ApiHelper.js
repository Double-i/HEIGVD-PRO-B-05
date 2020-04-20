const EZT_API = 'http://127.0.0.1:8080/api'

/**
 * Send a request to EzTools API
 *
 * @param {String} endpoint of the Easytool api
 * @param {String} verb HTTP verb
 * @param {Object} data data of the request
 * @param {Object} params used for GET request
 */
export function sendEzApiRequest(
    endpoint,
    verb = 'GET',
    data = {},
    params = {}
) {
    const url = prepareUrl(EZT_API.concat(endpoint), verb, params)
    return sendRequest(url, verb, data, params)
}
/**
 * Send the request, format it decode json and return the result as Promise
 *
 * @param {String} url
 * @param {String} verb
 * @param {Object} data
 */
export function sendRequest(url, verb = 'GET', data = {}) {
    const requestInfo = {
        method: verb,
        credentials: 'include',
        mode: 'cors',
        headers: {
            'Content-Type': 'application/json',
        },
    }
    if (verb !== 'GET' && verb !== 'HEAD') {
        requestInfo.body = JSON.stringify(data) // body data type must match "Content-Type" header
    }
    return fetch(url, requestInfo).then(res => res.json())
}
/**
 * Prepare URL with parameter if GET params given
 *
 * @param {String} url
 * @param {String} verb Http verb
 * @param {Object} params params needed for get request
 */
export function prepareUrl(strURL, verb = 'GET', params = {}) {
    const url = new URL(strURL)
    if (verb === 'GET') {
        // Add GET params to url
        Object.keys(params).forEach(key =>
            url.searchParams.append(key, params[key])
        )
    }
    return url
}
