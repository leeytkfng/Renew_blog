import {useNavigate} from "react-router-dom";
import {Bell} from 'lucide-react'
import {useAuth} from "../../hooks/useAuth.tsx";


export default function Header() {
    const navigate = useNavigate();
    const {user , isLoading, isAuthenticated , logout} = useAuth();

    const handleLogout = async () =>  {
         try {
             await logout();
             navigate('/');
         } catch (error) {
             console.error('Logout failed', error);
         }
    };

    if (isLoading) {
        return (
            <header className="border-b border-gray-200 bg-white sticky top-0 z-50">
                <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
                    <div className="flex items-center justify-between h-16">
                        <div className="flex items-center">
                            <h1 className="text-2xl font-bold text-black">Board</h1>
                            <div className="ml-2 px-2 py-1 bg-black text-white text-xs font-semibold">
                                AI
                            </div>
                        </div>
                        <div className="text-sm text-gray-500">로딩중...</div>
                    </div>
                </div>
            </header>
        );
    }

    return (
        <header className="border-b border-gray-200 bg-white sticky top-0 z-50">
            <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
                <div className="flex items-center justify-between h-16">
                    {/* Logo */}
                    <div
                        className="flex items-center cursor-pointer"
                        onClick={() => navigate('/')}
                    >
                        <h1 className="text-2xl font-bold text-black">Auction</h1>
                        <div className="ml-2 px-2 py-1 bg-black text-white text-xs font-semibold">
                            To-AI
                        </div>
                    </div>

                    {/* Navigation */}
                    <nav className="hidden md:flex items-center space-x-8">
                        <button
                            onClick={() => navigate('/auctions')}
                            className="text-black font-medium hover:text-gray-600 transition-colors"
                        >
                            경매 목록
                        </button>
                        <button
                            onClick={() => navigate('/ai-recommendations')}
                            className="text-black font-medium hover:text-gray-600 transition-colors"
                        >
                            AI 추천
                        </button>
                        {isAuthenticated && (
                            <>
                                <button
                                    onClick={() => navigate('/my-bids')}
                                    className="text-black font-medium hover:text-gray-600 transition-colors"
                                >
                                    내 입찰
                                </button>
                                <button className="text-gray-600 hover:text-black transition-colors">
                                    <Bell className="w-5 h-5" />
                                </button>
                            </>
                        )}
                    </nav>

                    {/* Auth Buttons */}
                    <div className="flex items-center space-x-4">
                        {isAuthenticated ? (
                            <>
                                <span className="text-sm text-gray-600">
                                    {user?.name}님
                                </span>
                                <button
                                    onClick={handleLogout}
                                    className="text-black font-medium hover:text-gray-600 transition-colors"
                                >
                                    로그아웃
                                </button>
                                <button
                                    onClick={() => navigate('/mypage')}
                                    className="px-4 py-2 bg-black text-white font-medium hover:bg-gray-900 transition-colors"
                                >
                                    마이페이지
                                </button>
                            </>
                        ) : (
                            <>
                                <button
                                    onClick={() => navigate('/login')}
                                    className="text-black font-medium hover:text-gray-600 transition-colors"
                                >
                                    로그인
                                </button>
                                <button
                                    onClick={() => navigate('/signup')}
                                    className="px-4 py-2 bg-black text-white font-medium hover:bg-gray-900 transition-colors"
                                >
                                    회원가입
                                </button>
                            </>
                        )}
                    </div>
                </div>
            </div>
        </header>
    )
}