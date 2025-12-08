import {BrowserRouter, Link, Route, Routes} from "react-router-dom";
import LoginPage from "./pages/auth/LoginPage.tsx";
import React from "react";
import SignUp from "./pages/auth/SignUp.tsx";
import MainPage from "./pages/MainAuction.tsx";
import Layout from "./components/layout/Layout.tsx";

function App() : React.ReactElement {

  return (
      <div>
          <div>
              <Routes>
                  <Route path="/login" element={<LoginPage/>} />
                  <Route path="/signup" element={<SignUp/>}/>

                  <Route path="/" element={<Layout><MainPage/></Layout>} />
              </Routes>
          </div>
      </div>
  )
}

export default App;
