import React from 'react'
import ReactDOM from 'react-dom/client'
import App from './App.jsx'
import './index.css'
import { BrowserRouter,Routes } from 'react-router-dom'

import AllRouts from './Routes/AllRouts.jsx'

ReactDOM.createRoot(document.getElementById('root')).render(
  <React.StrictMode>
    <BrowserRouter>
    <Routes>
   
    </Routes>
    <AllRouts/>
   
    </BrowserRouter>
  </React.StrictMode>,
)
