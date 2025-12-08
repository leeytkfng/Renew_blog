

export default function Footer(){
   return (
       <footer className="bg-white border-t border-gray-200 py-12 px-4">
           <div className="max-w-7xl mx-auto">
               <div className="grid grid-cols-1 md:grid-cols-4 gap-8 mb-8">
                   <div>
                       <h3 className="text-xl font-bold text-black mb-4">Board</h3>
                       <p className="text-gray-600 text-sm">
                           AI 기반 부동산 경매 플랫폼
                       </p>
                   </div>
                   <div>
                       <h4 className="font-bold text-black mb-4">서비스</h4>
                       <ul className="space-y-2 text-sm text-gray-600">
                           <li><a href="#" className="hover:text-black">경매 목록</a></li>
                           <li><a href="#" className="hover:text-black">AI 분석</a></li>
                           <li><a href="#" className="hover:text-black">입찰 가이드</a></li>
                       </ul>
                   </div>
                   <div>
                       <h4 className="font-bold text-black mb-4">고객지원</h4>
                       <ul className="space-y-2 text-sm text-gray-600">
                           <li><a href="#" className="hover:text-black">FAQ</a></li>
                           <li><a href="#" className="hover:text-black">문의하기</a></li>
                           <li><a href="#" className="hover:text-black">공지사항</a></li>
                       </ul>
                   </div>
                   <div>
                       <h4 className="font-bold text-black mb-4">회사</h4>
                       <ul className="space-y-2 text-sm text-gray-600">
                           <li><a href="#" className="hover:text-black">회사소개</a></li>
                           <li><a href="#" className="hover:text-black">이용약관</a></li>
                           <li><a href="#" className="hover:text-black">개인정보처리방침</a></li>
                       </ul>
                   </div>
               </div>
               <div className="border-t border-gray-200 pt-8 text-center text-sm text-gray-600">
                   © 2025 Board. All rights reserved.
               </div>
           </div>
       </footer>
   )
}