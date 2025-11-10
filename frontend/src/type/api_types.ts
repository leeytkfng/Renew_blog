
//공통 API 응답 타입
export interface ApiResponse<T = any> {
    success: boolean;
    message: string;
    data : T;
}

//페이지네이션 응답
export interface PageResponse<T> {
    content : T[],
    totalElements : number;
    totalPages: number;
    size:number;
    number:number;
}

export interface  ErrorResponse {
    success: false;
    message: string;
    code?: string;
    errors?: Record<string,string>
}