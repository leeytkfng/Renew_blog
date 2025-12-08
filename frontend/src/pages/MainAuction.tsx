import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import {
    Search,
    TrendingUp,
    Clock,
    MapPin,
    Home,
    Building2,
    Sparkles,
    ChevronRight,
    Filter,
    Bell
} from 'lucide-react';

interface AuctionItem {
    id: string;
    title: string;
    location: string;
    currentPrice: number;
    startPrice: number;
    endTime: string;
    imageUrl: string;
    category: string;
    aiScore: number;
}

export default function MainPage() {
    const navigate = useNavigate();
    const [searchQuery, setSearchQuery] = useState({
        location: '',
        category: '',
        priceRange: '',
    });

    // 임시 데이터
    const hotAuctions: AuctionItem[] = [
        {
            id: '1',
            title: '강남구 역삼동 오피스텔',
            location: '서울 강남구 역삼동',
            currentPrice: 450000000,
            startPrice: 400000000,
            endTime: '2시간 23분',
            imageUrl: '/api/placeholder/400/300',
            category: '오피스텔',
            aiScore: 92,
        },
        {
            id: '2',
            title: '판교 테크노밸리 상가',
            location: '경기 성남시 분당구',
            currentPrice: 780000000,
            startPrice: 700000000,
            endTime: '5시간 10분',
            imageUrl: '/api/placeholder/400/300',
            category: '상가',
            aiScore: 88,
        },
        {
            id: '3',
            title: '송파구 잠실 아파트',
            location: '서울 송파구 잠실동',
            currentPrice: 1200000000,
            startPrice: 1100000000,
            endTime: '1일 3시간',
            imageUrl: '/api/placeholder/400/300',
            category: '아파트',
            aiScore: 95,
        },
        {
            id: '4',
            title: '해운대 오션뷰 빌라',
            location: '부산 해운대구',
            currentPrice: 320000000,
            startPrice: 280000000,
            endTime: '12시간 45분',
            imageUrl: '/api/placeholder/400/300',
            category: '빌라',
            aiScore: 85,
        },
    ];

    const aiRecommendations: AuctionItem[] = [
        {
            id: '5',
            title: '마포구 합정동 빌딩',
            location: '서울 마포구 합정동',
            currentPrice: 2500000000,
            startPrice: 2300000000,
            endTime: '2일 5시간',
            imageUrl: '/api/placeholder/400/300',
            category: '빌딩',
            aiScore: 97,
        },
        {
            id: '6',
            title: '광진구 건대입구 원룸',
            location: '서울 광진구 화양동',
            currentPrice: 180000000,
            startPrice: 160000000,
            endTime: '8시간 30분',
            imageUrl: '/api/placeholder/400/300',
            category: '원룸',
            aiScore: 90,
        },
        {
            id: '7',
            title: '용산구 이태원 상가주택',
            location: '서울 용산구 이태원동',
            currentPrice: 950000000,
            startPrice: 880000000,
            endTime: '1일 18시간',
            imageUrl: '/api/placeholder/400/300',
            category: '상가주택',
            aiScore: 93,
        },
    ];

    const formatPrice = (price: number) => {
        if (price >= 100000000) {
            return `${(price / 100000000).toFixed(1)}억`;
        }
        return `${(price / 10000).toFixed(0)}만`;
    };

    return (
        <div className="min-h-screen bg-white">
            {/* Hero Section - 검색 중심 */}
            <section className="py-20 px-4">
                <div className="max-w-4xl mx-auto text-center">
                    <div className="inline-flex items-center px-4 py-2 bg-gray-100 rounded-full mb-6">
                        <Sparkles className="w-4 h-4 mr-2" />
                        <span className="text-sm font-medium">AI 기반 부동산 경매</span>
                    </div>

                    <h1 className="text-5xl md:text-6xl font-bold text-black mb-6 leading-tight">
                        AI가 분석한<br />
                        최적의 부동산 경매
                    </h1>

                    <p className="text-xl text-gray-600 mb-12">
                        실시간 시세 분석으로 현명한 투자를 시작하세요
                    </p>

                    {/* 통합 검색창 */}
                    <div className="bg-white border-2 border-black p-2 flex flex-col md:flex-row gap-2">
                        {/* 지역 */}
                        <div className="flex-1 flex items-center px-4 py-3 border-b md:border-b-0 md:border-r border-gray-200">
                            <MapPin className="w-5 h-5 text-gray-400 mr-3" />
                            <input
                                type="text"
                                placeholder="지역을 입력하세요"
                                className="flex-1 outline-none text-black placeholder-gray-400"
                                value={searchQuery.location}
                                onChange={(e) => setSearchQuery({ ...searchQuery, location: e.target.value })}
                            />
                        </div>

                        {/* 매물 유형 */}
                        <div className="flex-1 flex items-center px-4 py-3 border-b md:border-b-0 md:border-r border-gray-200">
                            <Home className="w-5 h-5 text-gray-400 mr-3" />
                            <select
                                className="flex-1 outline-none text-black bg-transparent"
                                value={searchQuery.category}
                                onChange={(e) => setSearchQuery({ ...searchQuery, category: e.target.value })}
                            >
                                <option value="">매물 유형</option>
                                <option value="apartment">아파트</option>
                                <option value="villa">빌라</option>
                                <option value="officetel">오피스텔</option>
                                <option value="commercial">상가</option>
                                <option value="building">빌딩</option>
                            </select>
                        </div>

                        {/* 가격대 */}
                        <div className="flex-1 flex items-center px-4 py-3">
                            <Filter className="w-5 h-5 text-gray-400 mr-3" />
                            <select
                                className="flex-1 outline-none text-black bg-transparent"
                                value={searchQuery.priceRange}
                                onChange={(e) => setSearchQuery({ ...searchQuery, priceRange: e.target.value })}
                            >
                                <option value="">가격대</option>
                                <option value="0-300">3억 이하</option>
                                <option value="300-500">3억 ~ 5억</option>
                                <option value="500-1000">5억 ~ 10억</option>
                                <option value="1000+">10억 이상</option>
                            </select>
                        </div>

                        {/* 검색 버튼 */}
                        <button className="px-8 py-3 bg-black text-white font-semibold hover:bg-gray-900 transition-colors flex items-center justify-center">
                            <Search className="w-5 h-5 mr-2" />
                            검색
                        </button>
                    </div>

                    {/* 인기 검색어 */}
                    <div className="mt-6 flex items-center justify-center gap-2 flex-wrap">
                        <span className="text-sm text-gray-500">인기 검색:</span>
                        {['강남', '판교', '잠실', '여의도', '송도'].map((keyword) => (
                            <button
                                key={keyword}
                                className="px-3 py-1 text-sm border border-gray-300 hover:border-black hover:bg-black hover:text-white transition-all"
                            >
                                {keyword}
                            </button>
                        ))}
                    </div>
                </div>
            </section>

            {/* Stats Section - 블랙 배경 */}
            <section className="bg-black text-white py-12">
                <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
                    <div className="grid grid-cols-1 md:grid-cols-3 gap-8">
                        {/* 진행중 경매 */}
                        <div className="flex items-start space-x-4">
                            <div className="w-12 h-12 bg-white/10 rounded-lg flex items-center justify-center flex-shrink-0">
                                <TrendingUp className="w-6 h-6" />
                            </div>
                            <div>
                                <p className="text-gray-400 text-sm mb-1">진행중 경매</p>
                                <p className="text-4xl font-bold">125</p>
                                <p className="text-sm text-gray-400 mt-1">건</p>
                            </div>
                        </div>

                        {/* 마감 임박 */}
                        <div className="flex items-start space-x-4">
                            <div className="w-12 h-12 bg-white/10 rounded-lg flex items-center justify-center flex-shrink-0">
                                <Clock className="w-6 h-6" />
                            </div>
                            <div>
                                <p className="text-gray-400 text-sm mb-1">오늘 마감</p>
                                <p className="text-4xl font-bold">42</p>
                                <p className="text-sm text-gray-400 mt-1">건</p>
                            </div>
                        </div>

                        {/* AI 분석 완료 */}
                        <div className="flex items-start space-x-4">
                            <div className="w-12 h-12 bg-white/10 rounded-lg flex items-center justify-center flex-shrink-0">
                                <Sparkles className="w-6 h-6" />
                            </div>
                            <div>
                                <p className="text-gray-400 text-sm mb-1">AI 분석 완료</p>
                                <p className="text-4xl font-bold">1,247</p>
                                <p className="text-sm text-gray-400 mt-1">건</p>
                            </div>
                        </div>
                    </div>
                </div>
            </section>

            {/* Hot Auctions - 화이트 배경 */}
            <section className="py-20 px-4">
                <div className="max-w-7xl mx-auto">
                    {/* 섹션 헤더 */}
                    <div className="flex items-center justify-between mb-12">
                        <div>
                            <div className="flex items-center mb-2">
                                <div className="w-8 h-8 bg-red-500 rounded-full flex items-center justify-center mr-3">
                                    <span className="text-white text-lg">🔥</span>
                                </div>
                                <h2 className="text-3xl font-bold text-black">실시간 인기 경매</h2>
                            </div>
                            <p className="text-gray-600">가장 많은 관심을 받고 있는 매물</p>
                        </div>
                        <button className="flex items-center text-black font-semibold hover:underline">
                            전체보기
                            <ChevronRight className="w-5 h-5 ml-1" />
                        </button>
                    </div>

                    {/* 카드 그리드 */}
                    <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
                        {hotAuctions.map((auction) => (
                            <div
                                key={auction.id}
                                className="group cursor-pointer border-2 border-gray-200 hover:border-black transition-all"
                            >
                                {/* 이미지 */}
                                <div className="relative aspect-[4/3] bg-gray-200 overflow-hidden">
                                    <img
                                        src={auction.imageUrl}
                                        alt={auction.title}
                                        className="w-full h-full object-cover group-hover:scale-105 transition-transform duration-300"
                                    />
                                    {/* AI 점수 배지 */}
                                    <div className="absolute top-3 right-3 px-2 py-1 bg-black text-white text-xs font-semibold flex items-center">
                                        <Sparkles className="w-3 h-3 mr-1" />
                                        AI {auction.aiScore}
                                    </div>
                                    {/* 카테고리 배지 */}
                                    <div className="absolute top-3 left-3 px-2 py-1 bg-white text-black text-xs font-semibold">
                                        {auction.category}
                                    </div>
                                </div>

                                {/* 정보 */}
                                <div className="p-4">
                                    <h3 className="font-bold text-black mb-2 line-clamp-1">
                                        {auction.title}
                                    </h3>
                                    <p className="text-sm text-gray-600 mb-3 flex items-center">
                                        <MapPin className="w-4 h-4 mr-1" />
                                        {auction.location}
                                    </p>

                                    {/* 가격 정보 */}
                                    <div className="mb-3">
                                        <p className="text-xs text-gray-500 mb-1">시작가: {formatPrice(auction.startPrice)}</p>
                                        <p className="text-2xl font-bold text-black">{formatPrice(auction.currentPrice)}</p>
                                    </div>

                                    {/* 남은 시간 */}
                                    <div className="flex items-center justify-between pt-3 border-t border-gray-200">
                                        <div className="flex items-center text-sm text-gray-600">
                                            <Clock className="w-4 h-4 mr-1" />
                                            {auction.endTime}
                                        </div>
                                        <button className="text-black font-semibold text-sm hover:underline">
                                            입찰하기
                                        </button>
                                    </div>
                                </div>
                            </div>
                        ))}
                    </div>
                </div>
            </section>

            {/* AI Recommendations - 블랙 배경 */}
            <section className="bg-black text-white py-20 px-4">
                <div className="max-w-7xl mx-auto">
                    {/* 섹션 헤더 */}
                    <div className="text-center mb-12">
                        <div className="inline-flex items-center px-4 py-2 bg-white/10 rounded-full mb-4">
                            <Sparkles className="w-4 h-4 mr-2" />
                            <span className="text-sm font-medium">AI Powered</span>
                        </div>
                        <h2 className="text-4xl font-bold mb-4">당신을 위한 AI 추천</h2>
                        <p className="text-gray-400 text-lg">
                            검색 패턴과 선호도를 분석하여 최적의 매물을 추천합니다
                        </p>
                    </div>

                    {/* 추천 카드 그리드 */}
                    <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
                        {aiRecommendations.map((auction) => (
                            <div
                                key={auction.id}
                                className="group cursor-pointer bg-white/5 border border-white/10 hover:border-white/30 transition-all"
                            >
                                {/* 이미지 */}
                                <div className="relative aspect-[4/3] bg-gray-800 overflow-hidden">
                                    <img
                                        src={auction.imageUrl}
                                        alt={auction.title}
                                        className="w-full h-full object-cover group-hover:scale-105 transition-transform duration-300"
                                    />
                                    {/* AI 점수 배지 */}
                                    <div className="absolute top-3 right-3 px-3 py-1 bg-white text-black text-sm font-bold flex items-center">
                                        <Sparkles className="w-4 h-4 mr-1" />
                                        {auction.aiScore}점
                                    </div>
                                </div>

                                {/* 정보 */}
                                <div className="p-5">
                                    <div className="text-xs text-gray-400 mb-2">{auction.category}</div>
                                    <h3 className="font-bold text-white mb-2 text-lg">
                                        {auction.title}
                                    </h3>
                                    <p className="text-sm text-gray-400 mb-4 flex items-center">
                                        <MapPin className="w-4 h-4 mr-1" />
                                        {auction.location}
                                    </p>

                                    {/* 가격 */}
                                    <div className="mb-4">
                                        <p className="text-2xl font-bold text-white">{formatPrice(auction.currentPrice)}</p>
                                    </div>

                                    <div className="p-3 bg-white/5 border border-white/10 mb-4">
                                        <p className="text-xs text-gray-400 mb-1">AI 추천 이유</p>
                                        <p className="text-sm text-white">
                                            최근 관심 지역 및 가격대와 일치합니다
                                        </p>
                                    </div>

                                    <button className="w-full py-3 bg-white text-black font-semibold hover:bg-gray-200 transition-colors flex items-center justify-center">
                                        자세히 보기
                                        <ChevronRight className="w-5 h-5 ml-1" />
                                    </button>
                                </div>
                            </div>
                        ))}
                    </div>

                    <div className="text-center mt-12">
                        <button className="px-8 py-4 border-2 border-white text-white font-semibold hover:bg-white hover:text-black transition-all">
                            더 많은 AI 추천 보기
                        </button>
                    </div>
                </div>
            </section>

            <section className="py-20 px-4 bg-gray-50">
                <div className="max-w-7xl mx-auto">
                    <div className="text-center mb-16">
                        <h2 className="text-4xl font-bold text-black mb-4">AI가 분석하는 방법</h2>
                        <p className="text-xl text-gray-600">
                            복잡한 부동산 데이터를 AI가 간단하게 분석합니다
                        </p>
                    </div>

                    <div className="grid grid-cols-1 md:grid-cols-4 gap-8">
                        {/* Step 1 */}
                        <div className="text-center">
                            <div className="w-16 h-16 bg-black text-white rounded-full flex items-center justify-center text-2xl font-bold mx-auto mb-4">
                                1
                            </div>
                            <h3 className="text-xl font-bold text-black mb-2">데이터 수집</h3>
                            <p className="text-gray-600">
                                실시간 시세, 거래 내역, 지역 정보 수집
                            </p>
                        </div>

                        {/* Step 2 */}
                        <div className="text-center">
                            <div className="w-16 h-16 bg-black text-white rounded-full flex items-center justify-center text-2xl font-bold mx-auto mb-4">
                                2
                            </div>
                            <h3 className="text-xl font-bold text-black mb-2">AI 분석</h3>
                            <p className="text-gray-600">
                                머신러닝으로 가치와 리스크 분석
                            </p>
                        </div>

                        {/* Step 3 */}
                        <div className="text-center">
                            <div className="w-16 h-16 bg-black text-white rounded-full flex items-center justify-center text-2xl font-bold mx-auto mb-4">
                                3
                            </div>
                            <h3 className="text-xl font-bold text-black mb-2">점수 산출</h3>
                            <p className="text-gray-600">
                                투자 가치를 0-100점으로 계산
                            </p>
                        </div>

                        {/* Step 4 */}
                        <div className="text-center">
                            <div className="w-16 h-16 bg-black text-white rounded-full flex items-center justify-center text-2xl font-bold mx-auto mb-4">
                                4
                            </div>
                            <h3 className="text-xl font-bold text-black mb-2">추천 제공</h3>
                            <p className="text-gray-600">
                                개인화된 맞춤 매물 추천
                            </p>
                        </div>
                    </div>
                </div>
            </section>

            {/* CTA Section - 블랙 배경 */}
            <section className="bg-black text-white py-20 px-4">
                <div className="max-w-4xl mx-auto text-center">
                    <h2 className="text-4xl md:text-5xl font-bold mb-6">
                        지금 시작하세요
                    </h2>
                    <p className="text-xl text-gray-400 mb-10">
                        AI 분석으로 더 똑똑한 부동산 투자를 경험하세요
                    </p>
                    <div className="flex flex-col sm:flex-row gap-4 justify-center">
                        <button
                            onClick={() => navigate('/signup')}
                            className="px-8 py-4 bg-white text-black font-bold hover:bg-gray-200 transition-all text-lg"
                        >
                            무료로 시작하기
                        </button>
                        <button className="px-8 py-4 border-2 border-white text-white font-bold hover:bg-white hover:text-black transition-all text-lg">
                            더 알아보기
                        </button>
                    </div>
                </div>
            </section>
        </div>
    );
}