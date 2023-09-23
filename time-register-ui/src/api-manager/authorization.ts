import axios from "axios";
import { createCookie } from "./cookie-helper";
import { getLocalApiUrl } from "./request-helper";

export async function getAuthToken(userAuthorization: string) {
    try {
        axios.defaults.headers.common['Authorization'] = userAuthorization
        const { data, status } = await axios.post<string>(
            getLocalApiUrl() + "authentication",
            {
                headers: {
                    Accept: "*/*",
                    'Access-Control-Allow-Origin': '*'
                },
            }

        );
        return { authToken: data, responseStatus: status }
    } catch (error) {
        return { authToken: null, responseStatus: status, error: error }
    }
}