import {useEffect, useState} from "react";
import type {SignupRequest, UserInfo} from "../type/auth_types.ts";
import {AuthStatus} from "../type/auth_types.ts";
import {authApi} from "../api/authApi.ts";

export const useAuth = () => {
    const [user, setUser] = useState<UserInfo | null>( null);
    const [status, setStatus] = useState<AuthStatus>(AuthStatus.LOADING);

    useEffect(() => {
        checkAuth();
    }, []);

    const checkAuth = () => {
        try {
            const currentUser = authApi.getCurrentUser();
            const isAuth = authApi.isAuthenticated();

            if (isAuth && currentUser) {
                setUser(currentUser);
                setStatus(AuthStatus.AUTHENTICATED);
            } else {
                setUser(null);
                setStatus(AuthStatus.UNAUTHENTICATED);
            }
        } catch (error) {
            console.error('Auth check failed:', error);
            setUser(null);
            setStatus(AuthStatus.UNAUTHENTICATED);
        }
    };

    const login = async (email:string, password: string) => {
        try {
            setStatus(AuthStatus.LOADING);

            const response = await authApi.login({email, password});

            const userInfo : UserInfo = {
                userId: response.userId,
                email: response.email,
                name: response.name,
            }

            setUser(userInfo);
            setStatus(AuthStatus.AUTHENTICATED);


            console.log('✅ [useAuth] 로그인 성공');

            //추가: localStorage에서 다시 읽어서 확인
            console.log('✅ localStorage 확인:', {
                accessToken: localStorage.getItem('accessToken')?.substring(0, 20) + '...',
                refreshToken: localStorage.getItem('refreshToken')?.substring(0, 20) + '...',
                user: localStorage.getItem('user')
            });


            return response;
         } catch (error: any) {
            setStatus(AuthStatus.UNAUTHENTICATED);
            throw new Error(error.response?.data?.message || '로그인에 실패했습니다.');
        }
    }

    const signup = async (data: SignupRequest) => {
        try {
            const response = await authApi.signup(data);
            return response;
        } catch (error: any) {
            throw new Error(error.response?.data?.message || '회원가입에 실패했습니다.');
        }
    };

    const logout = async () => {
        try {
            await authApi.logout();
        } finally {
            setUser(null);
            setStatus(AuthStatus.UNAUTHENTICATED);
        }
    };

    return {
        user,
        status,
        isAuthenticated: status === AuthStatus.AUTHENTICATED,
        isLoading: status === AuthStatus.LOADING,
        login,
        signup,
        logout,
        checkAuth,
    };


}