import { prepareUrl, sendRequest } from "./ApiHelper"

const GOOGLE_API_KEY = "AIzaSyCDaaFc6krVmxR43Be81BVgYuXIbXLE1r8"; // voir telegrame
const GOOGLE_API_BASE_URL = "https://maps.googleapis.com/maps/api/geocode/json?";

/**
 * This function check with google API if the given address is valid. It returns an array,
 * the first value is a int which indicates if the address is valid (1), not precise enough (-1),
 * several result (-2) or invalid (0)
 *
 * the second value is the address, can be empty if first value is false
 *
 * @param {Object} addressData
 */
export function checkAddress(addressData) {
    const url = getSearchAddressUrl(addressData)
    return fetch(url)
        .then((response) => {
            return response.json();
        })
        .then((result) => {
            let address
            // Address seems valid
            if (result.status === "OK") {
                // several results
                if (result.results.length > 1) {
                    return [-2, {}]
                }
                // One result
                else {
                    address = processAddress(result.results[0])
                    const isValid = "street" in address ? 1 : -1
                    return [isValid, address]
                }
            }
            console.log("Adresse Invalide")
            return [0, address]

        });
}
/**
 * Transform a google format address into an Object with the different part of the address we need
 *
 * @param {Object} googleAddress
 */
function processAddress(googleAddress) {
    const address = {}
    const mappingGoogle = {
        street_number: "streetNumber",
        route: "street",
        locality: "city",
        country: "country",
        postal_code: "zipCode"
    }
    // create adresse with
    googleAddress.address_components.forEach(component => {
        Object.keys(mappingGoogle).forEach(key => {
            if (component.types.includes(key)) {
                address[mappingGoogle[key]] = component.long_name
            }
        })
    })
    address.lat = googleAddress.geometry.location.lat
    address.long = googleAddress.geometry.location.lng

    return address
}

/**
 * Create URL for search Google address and return it
 *
 * @param {Object} addressData
 */
function getSearchAddressUrl(addressData) {
    let address = ""
    // Concat address in one string
    Object.keys(addressData).forEach(
        key => (address = address.concat(addressData[key], " "))
    )
    return prepareUrl(GOOGLE_API_BASE_URL, "GET", {
        key: GOOGLE_API_KEY,
        address: address.trim()
    })
}
