import { createRoot } from 'react-dom/client'
import './index.css'
import App from './App.tsx'
import {BrowserRouter} from "react-router-dom";
import React from "react";

createRoot(document.getElementById('root')!).render(
  <React.StrictMode>
      <BrowserRouter>
          <App />
      </BrowserRouter>
  </React.StrictMode>
)

/**
 * 오류 #1 발생한이유 jsx -> ReactNode 로 변
 * react-jsx 자동 jsx 변환이 설정이 되어있어도
 * React.StrictMode같이 React 네임스페이스가 필요한 경우에는 import React 설정이 없을경우 깨짐.
 */