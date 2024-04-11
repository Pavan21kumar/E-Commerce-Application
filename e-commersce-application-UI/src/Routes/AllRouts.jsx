import React from 'react'
import Rout from 'react-rout'
import { BrowserRouter,Routes, Route} from 'react-router-dom'
import Cart from '../Private/Customer/Cart'
import WishList from '../Private/Customer/WishList'
import EditProfile from '../Private/Common/EditProfile'
import SellerDashboard from '../Private/Seller/SellerDashboard'
import AddProduct from '../Private/Seller/AddProduct'
import Login from '../Public/Login'
import Register from '../Public/Register'
import VerifyOtp from '../Public/VerifyOtp'
import App from '../App'
import Home from '../Public/Home'
import Explore from '../Private/Customer/Explore'
import AddAddress from '../Private/Common/AddAdress'

const AllRouts = () => {
   
       const user={

        userId:"123",
        username:"pavan",
        role:"CUSTOMER",
        authenticated:true,
        acessExpairation:3600,
        refreshExpiration:12396000
       }
       const{role,authenticated}=user
       let routes=[];
       if(authenticated){
        if(role=="CUSTOMER")
        {
         routes.push(<Route key="Cart" path='/cart' element={<Cart/>}/>)
         routes.push(<Route key="WishList" path='/wishlist' element={<WishList/>} />)
         routes.push(<Route key="EditProfile" path='/editProfile' element={<EditProfile/>} />,
         <Route path='/explore' key="Explore" element={<Explore/>} />)
     
        }
        else if(role=="SELLER")
        {
         routes.push( <Route key="EditProfile"   path='/editProfile' element={<EditProfile/>}/>)
         routes.push(<Route  key="SellerDashboard" path='sellerDashboard' element={<SellerDashboard/>}/>)
         routes.push(<Route  key="Add Product" path='addProduct' element={<AddProduct/>} />)

        }  
     routes.push(<Route path='/addAddress' key="Add Address" element={<AddAddress/>} />,
     <Route path='/'key="Home" element={<Home/>}/> )

 }
 else{
     // render routes that are public and visible before login
     routes.push(<Route key="Home" path='/' element={<Home/>} />)
     routes.push(<Route key="Login" path='login' element={<Login/>} />)
     routes.push(<Route key="Register" path='register' element={<Register/>}/>)
     routes.push(<Route key="Verify Otp" path='verifyOtp' element={<VerifyOtp/>}/>,
     <Route path='/explore' key="Explore" element={<Explore/>} />)
     
 }

 return <Routes> <Route path='/' element={<App/>} >{routes}</Route></Routes>
}



export default AllRouts

/**
 * 





*/