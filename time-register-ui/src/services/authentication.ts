import { getAuthToken } from "../api-manager/authorization"
import { createCookie } from "../api-manager/cookie-helper"

export function encyptLoginData(userData: string) {
    console.log(Buffer.from(userData).toString('base64'))
    return Buffer.from(userData).toString('base64')
}

export async function handlerUserAuthRequest(encyptLoginData: string) {
    let result: Boolean = false
    const { authToken, responseStatus } = await getAuthToken(encyptLoginData)

    if (responseStatus === 202 && authToken !== null) {
        result = true
        createCookie("user", authToken)
    }

    return result
}