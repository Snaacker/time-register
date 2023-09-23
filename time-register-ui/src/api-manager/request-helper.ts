import { getCookie } from "./cookie-helper"

export function getLocalApiUrl() {
    return "http://localhost:8080/api/v1/"
}

export function getRequestHeader() {
    return {
        Accept: "application/json",
        'Access-Control-Allow-Origin': '*',
        Authorization: "Bearer " + getCookie("user")
    }
}