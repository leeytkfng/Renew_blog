import React from "react";
import Footer from "./Footer.tsx";
import Header from "./Header.tsx";


interface LayoutProps {
    children : React.ReactNode;
}

export default function Layout({children} : LayoutProps) {
    return (
        <div className="min-h-screen flex flex-col">
            <Header />
            <main className="flex-1">
                {children}
            </main>
            <Footer />
        </div>
    );
}