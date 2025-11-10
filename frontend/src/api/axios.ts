import axios, { AxiosError, AxiosInstance, InternalAxiosRequestConfig, AxiosResponse } from 'axios';
import { ErrorResponse } from '../type/api_types.ts'


const axiosInstance : AxiosInstance = axios.create({
    baseURL: '/api',
    timeout: 10000,
    headers: {
        'Content-Type' : 'application/json',
    },
    withCredentials: true,
});


axiosInstance.interceptors.request.use(
    (config: InternalAxiosRequestConfig) => {

    }
)