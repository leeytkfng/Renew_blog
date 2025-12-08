import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { Mail, Lock, User, Phone, MapPin, Eye, EyeOff, ArrowRight } from 'lucide-react';

interface SignUpForm {
    email: string;
    password: string;
    passwordConfirm: string;
    name: string;
    phone: string;
    address: string;
}

export default function SignUpPage() {
    const navigate = useNavigate();
    const [formData, setFormData] = useState<SignUpForm>({
        email: '',
        password: '',
        passwordConfirm: '',
        name: '',
        phone: '',
        address: '',
    });

    const [errors, setErrors] = useState<Partial<SignUpForm>>({});
    const [isLoading, setIsLoading] = useState(false);
    const [showPassword, setShowPassword] = useState(false);
    const [showPasswordConfirm, setShowPasswordConfirm] = useState(false);

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target;
        setFormData(prev => ({ ...prev, [name]: value }));
        if (errors[name as keyof SignUpForm]) {
            setErrors(prev => ({ ...prev, [name]: '' }));
        }
    };

    const validateForm = (): boolean => {
        const newErrors: Partial<SignUpForm> = {};

        if (!formData.email) {
            newErrors.email = '이메일을 입력해주세요';
        } else if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(formData.email)) {
            newErrors.email = '올바른 이메일 형식이 아닙니다';
        }

        if (!formData.password) {
            newErrors.password = '비밀번호를 입력해주세요';
        } else if (formData.password.length < 8) {
            newErrors.password = '비밀번호는 8자 이상이어야 합니다';
        }

        if (!formData.passwordConfirm) {
            newErrors.passwordConfirm = '비밀번호 확인을 입력해주세요';
        } else if (formData.password !== formData.passwordConfirm) {
            newErrors.passwordConfirm = '비밀번호가 일치하지 않습니다';
        }

        if (!formData.name) {
            newErrors.name = '이름을 입력해주세요';
        }

        if (!formData.phone) {
            newErrors.phone = '전화번호를 입력해주세요';
        } else if (!/^010\d{8}$/.test(formData.phone.replace(/-/g, ''))) {
            newErrors.phone = '올바른 전화번호 형식이 아닙니다';
        }

        if (!formData.address) {
            newErrors.address = '주소를 입력해주세요';
        }

        setErrors(newErrors);
        return Object.keys(newErrors).length === 0;
    };

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        if (!validateForm()) return;

        setIsLoading(true);
        try {
            const response = await fetch('http://localhost:8080/api/auth/signup', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({
                    email: formData.email,
                    password: formData.password,
                    name: formData.name,
                    phone: formData.phone.replace(/-/g, ''),
                    address: formData.address,
                }),
            });

            if (!response.ok) throw new Error('회원가입에 실패했습니다');

            alert('회원가입이 완료되었습니다');
            navigate('/login');
        } catch (error) {
            console.error('회원가입 오류:', error);
            alert('회원가입 중 오류가 발생했습니다');
        } finally {
            setIsLoading(false);
        }
    };

    return (
        <div className="min-h-screen bg-white flex">
            {/* 왼쪽: 브랜드 섹션 */}
            <div className="hidden lg:flex lg:w-1/2 bg-black flex-col justify-between p-12">
                <div>
                    <h1 className="text-4xl font-bold text-white mb-2">Board</h1>
                    <div className="w-12 h-1 bg-white"></div>
                </div>

                <div className="space-y-8">
                    <div>
                        <h2 className="text-5xl font-bold text-white leading-tight mb-6">
                            시작하세요.<br />
                            지금 바로.
                        </h2>
                        <p className="text-gray-400 text-lg">
                            간단한 가입으로 모든 기능을 이용하실 수 있습니다.
                        </p>
                    </div>

                    <div className="space-y-4">
                        <div className="flex items-start space-x-3">
                            <div className="w-6 h-6 border border-white rounded-full flex items-center justify-center flex-shrink-0 mt-1">
                                <div className="w-2 h-2 bg-white rounded-full"></div>
                            </div>
                            <div>
                                <h3 className="text-white font-semibold">빠른 시작</h3>
                                <p className="text-gray-400 text-sm">몇 분 안에 모든 설정 완료</p>
                            </div>
                        </div>
                        <div className="flex items-start space-x-3">
                            <div className="w-6 h-6 border border-white rounded-full flex items-center justify-center flex-shrink-0 mt-1">
                                <div className="w-2 h-2 bg-white rounded-full"></div>
                            </div>
                            <div>
                                <h3 className="text-white font-semibold">안전한 보안</h3>
                                <p className="text-gray-400 text-sm">최신 보안 기술로 정보 보호</p>
                            </div>
                        </div>
                        <div className="flex items-start space-x-3">
                            <div className="w-6 h-6 border border-white rounded-full flex items-center justify-center flex-shrink-0 mt-1">
                                <div className="w-2 h-2 bg-white rounded-full"></div>
                            </div>
                            <div>
                                <h3 className="text-white font-semibold">무료 체험</h3>
                                <p className="text-gray-400 text-sm">모든 기능 무료로 사용</p>
                            </div>
                        </div>
                    </div>
                </div>

                <div className="text-gray-500 text-sm">
                    © 2025 Board. All rights reserved.
                </div>
            </div>

            {/* 오른쪽: 폼 섹션 */}
            <div className="flex-1 flex items-center justify-center p-8">
                <div className="w-full max-w-md">
                    {/* 모바일 헤더 */}
                    <div className="lg:hidden mb-8">
                        <h1 className="text-3xl font-bold text-black mb-1">Board</h1>
                        <div className="w-10 h-1 bg-black"></div>
                    </div>

                    {/* 폼 헤더 */}
                    <div className="mb-10">
                        <h2 className="text-3xl font-bold text-black mb-2">회원가입</h2>
                        <p className="text-gray-600">
                            이미 계정이 있으신가요?{' '}
                            <button
                                onClick={() => navigate('/login')}
                                className="text-black font-semibold hover:underline inline-flex items-center"
                            >
                                로그인
                                <ArrowRight className="w-4 h-4 ml-1" />
                            </button>
                        </p>
                    </div>

                    {/* 폼 */}
                    <form onSubmit={handleSubmit} className="space-y-6">
                        {/* 이메일 */}
                        <div>
                            <label className="block text-sm font-medium text-black mb-2">
                                이메일
                            </label>
                            <div className="relative">
                                <Mail className="absolute left-0 top-1/2 transform -translate-y-1/2 text-gray-400 w-5 h-5" />
                                <input
                                    type="email"
                                    name="email"
                                    value={formData.email}
                                    onChange={handleChange}
                                    className={`w-full pl-8 pr-4 py-3 bg-transparent border-b-2 ${
                                        errors.email
                                            ? 'border-red-500 focus:border-red-600'
                                            : 'border-gray-300 focus:border-black'
                                    } focus:outline-none transition-colors text-black placeholder-gray-400`}
                                    placeholder="example@email.com"
                                />
                            </div>
                            {errors.email && (
                                <p className="mt-2 text-sm text-red-600">{errors.email}</p>
                            )}
                        </div>

                        {/* 비밀번호 */}
                        <div>
                            <label className="block text-sm font-medium text-black mb-2">
                                비밀번호
                            </label>
                            <div className="relative">
                                <Lock className="absolute left-0 top-1/2 transform -translate-y-1/2 text-gray-400 w-5 h-5" />
                                <input
                                    type={showPassword ? 'text' : 'password'}
                                    name="password"
                                    value={formData.password}
                                    onChange={handleChange}
                                    className={`w-full pl-8 pr-12 py-3 bg-transparent border-b-2 ${
                                        errors.password
                                            ? 'border-red-500 focus:border-red-600'
                                            : 'border-gray-300 focus:border-black'
                                    } focus:outline-none transition-colors text-black placeholder-gray-400`}
                                    placeholder="8자 이상"
                                />
                                <button
                                    type="button"
                                    onClick={() => setShowPassword(!showPassword)}
                                    className="absolute right-0 top-1/2 transform -translate-y-1/2 text-gray-400 hover:text-black transition-colors"
                                >
                                    {showPassword ? <EyeOff className="w-5 h-5" /> : <Eye className="w-5 h-5" />}
                                </button>
                            </div>
                            {errors.password && (
                                <p className="mt-2 text-sm text-red-600">{errors.password}</p>
                            )}
                        </div>

                        {/* 비밀번호 확인 */}
                        <div>
                            <label className="block text-sm font-medium text-black mb-2">
                                비밀번호 확인
                            </label>
                            <div className="relative">
                                <Lock className="absolute left-0 top-1/2 transform -translate-y-1/2 text-gray-400 w-5 h-5" />
                                <input
                                    type={showPasswordConfirm ? 'text' : 'password'}
                                    name="passwordConfirm"
                                    value={formData.passwordConfirm}
                                    onChange={handleChange}
                                    className={`w-full pl-8 pr-12 py-3 bg-transparent border-b-2 ${
                                        errors.passwordConfirm
                                            ? 'border-red-500 focus:border-red-600'
                                            : 'border-gray-300 focus:border-black'
                                    } focus:outline-none transition-colors text-black placeholder-gray-400`}
                                    placeholder="비밀번호 재입력"
                                />
                                <button
                                    type="button"
                                    onClick={() => setShowPasswordConfirm(!showPasswordConfirm)}
                                    className="absolute right-0 top-1/2 transform -translate-y-1/2 text-gray-400 hover:text-black transition-colors"
                                >
                                    {showPasswordConfirm ? <EyeOff className="w-5 h-5" /> : <Eye className="w-5 h-5" />}
                                </button>
                            </div>
                            {errors.passwordConfirm && (
                                <p className="mt-2 text-sm text-red-600">{errors.passwordConfirm}</p>
                            )}
                        </div>

                        {/* 이름 */}
                        <div>
                            <label className="block text-sm font-medium text-black mb-2">
                                이름
                            </label>
                            <div className="relative">
                                <User className="absolute left-0 top-1/2 transform -translate-y-1/2 text-gray-400 w-5 h-5" />
                                <input
                                    type="text"
                                    name="name"
                                    value={formData.name}
                                    onChange={handleChange}
                                    className={`w-full pl-8 pr-4 py-3 bg-transparent border-b-2 ${
                                        errors.name
                                            ? 'border-red-500 focus:border-red-600'
                                            : 'border-gray-300 focus:border-black'
                                    } focus:outline-none transition-colors text-black placeholder-gray-400`}
                                    placeholder="홍길동"
                                />
                            </div>
                            {errors.name && (
                                <p className="mt-2 text-sm text-red-600">{errors.name}</p>
                            )}
                        </div>

                        {/* 전화번호 */}
                        <div>
                            <label className="block text-sm font-medium text-black mb-2">
                                전화번호
                            </label>
                            <div className="relative">
                                <Phone className="absolute left-0 top-1/2 transform -translate-y-1/2 text-gray-400 w-5 h-5" />
                                <input
                                    type="tel"
                                    name="phone"
                                    value={formData.phone}
                                    onChange={handleChange}
                                    className={`w-full pl-8 pr-4 py-3 bg-transparent border-b-2 ${
                                        errors.phone
                                            ? 'border-red-500 focus:border-red-600'
                                            : 'border-gray-300 focus:border-black'
                                    } focus:outline-none transition-colors text-black placeholder-gray-400`}
                                    placeholder="01012345678"
                                />
                            </div>
                            {errors.phone && (
                                <p className="mt-2 text-sm text-red-600">{errors.phone}</p>
                            )}
                        </div>

                        {/* 주소 */}
                        <div>
                            <label className="block text-sm font-medium text-black mb-2">
                                주소
                            </label>
                            <div className="relative">
                                <MapPin className="absolute left-0 top-1/2 transform -translate-y-1/2 text-gray-400 w-5 h-5" />
                                <input
                                    type="text"
                                    name="address"
                                    value={formData.address}
                                    onChange={handleChange}
                                    className={`w-full pl-8 pr-4 py-3 bg-transparent border-b-2 ${
                                        errors.address
                                            ? 'border-red-500 focus:border-red-600'
                                            : 'border-gray-300 focus:border-black'
                                    } focus:outline-none transition-colors text-black placeholder-gray-400`}
                                    placeholder="서울시 강남구"
                                />
                            </div>
                            {errors.address && (
                                <p className="mt-2 text-sm text-red-600">{errors.address}</p>
                            )}
                        </div>

                        {/* 약관 동의 */}
                        <div className="flex items-start space-x-3 pt-2">
                            <input
                                type="checkbox"
                                id="terms"
                                className="mt-1 w-4 h-4 border-2 border-gray-300 rounded focus:ring-0 focus:ring-offset-0 checked:bg-black checked:border-black"
                            />
                            <label htmlFor="terms" className="text-sm text-gray-600">
                                <span className="text-black font-medium">이용약관</span> 및{' '}
                                <span className="text-black font-medium">개인정보처리방침</span>에 동의합니다.
                            </label>
                        </div>

                        {/* 가입 버튼 */}
                        <button
                            type="submit"
                            disabled={isLoading}
                            className="w-full bg-black text-white py-4 font-semibold hover:bg-gray-900 focus:outline-none focus:ring-2 focus:ring-black focus:ring-offset-2 disabled:opacity-50 disabled:cursor-not-allowed transition-all group"
                        >
              <span className="flex items-center justify-center">
                {isLoading ? (
                    <>
                        <svg className="animate-spin h-5 w-5 mr-2" viewBox="0 0 24 24">
                            <circle className="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" strokeWidth="4" fill="none" />
                            <path className="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z" />
                        </svg>
                        처리 중...
                    </>
                ) : (
                    <>
                        가입하기
                        <ArrowRight className="w-5 h-5 ml-2 group-hover:translate-x-1 transition-transform" />
                    </>
                )}
              </span>
                        </button>
                    </form>

                    {/* 소셜 로그인 */}
                    <div className="mt-8">
                        <div className="relative">
                            <div className="absolute inset-0 flex items-center">
                                <div className="w-full border-t border-gray-300"></div>
                            </div>
                            <div className="relative flex justify-center text-sm">
                <span className="px-4 bg-white text-gray-500">
                  또는
                </span>
                            </div>
                        </div>

                        <div className="mt-6 grid grid-cols-2 gap-4">
                            <button className="flex items-center justify-center px-4 py-3 border-2 border-black hover:bg-black hover:text-white transition-all group">
                                <svg className="w-5 h-5 mr-2" viewBox="0 0 24 24">
                                    <path className="group-hover:fill-white" fill="#000" d="M22.56 12.25c0-.78-.07-1.53-.2-2.25H12v4.26h5.92c-.26 1.37-1.04 2.53-2.21 3.31v2.77h3.57c2.08-1.92 3.28-4.74 3.28-8.09z"/>
                                    <path className="group-hover:fill-white" fill="#000" d="M12 23c2.97 0 5.46-.98 7.28-2.66l-3.57-2.77c-.98.66-2.23 1.06-3.71 1.06-2.86 0-5.29-1.93-6.16-4.53H2.18v2.84C3.99 20.53 7.7 23 12 23z"/>
                                    <path className="group-hover:fill-white" fill="#000" d="M5.84 14.09c-.22-.66-.35-1.36-.35-2.09s.13-1.43.35-2.09V7.07H2.18C1.43 8.55 1 10.22 1 12s.43 3.45 1.18 4.93l2.85-2.22.81-.62z"/>
                                    <path className="group-hover:fill-white" fill="#000" d="M12 5.38c1.62 0 3.06.56 4.21 1.64l3.15-3.15C17.45 2.09 14.97 1 12 1 7.7 1 3.99 3.47 2.18 7.07l3.66 2.84c.87-2.6 3.3-4.53 6.16-4.53z"/>
                                </svg>
                                <span className="text-sm font-medium">Google</span>
                            </button>
                            <button className="flex items-center justify-center px-4 py-3 border-2 border-black hover:bg-black hover:text-white transition-all">
                                <span className="text-sm font-medium">Apple</span>
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}