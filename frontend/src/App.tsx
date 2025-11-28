import {BrowserRouter, Link, Route, Routes} from "react-router-dom";
import LoginPage from "./pages/LoginPage.tsx";
import React from "react";

function App() : React.ReactElement {

  return (
      <div>
          <div>
              <Routes>
                  <Route path="/" element={<LoginPage/>} />
              </Routes>
          </div>
      </div>
  )
}

export default App;
