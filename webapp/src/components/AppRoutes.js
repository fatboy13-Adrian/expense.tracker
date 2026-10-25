import {Routes, Route, Navigate} from "react-router-dom";
import DailyDashboard from "../dashboards/DailyDashboard";
import MonthlyDashboard from "../dashboards/MonthlyDashboard";

export default function AppRoutes () {
    return (
        <Routes>
            {/* <Route path = "/daily/form" 
            element = {<DailyForm/>}/> */}
            <Route path = "/daily/retrieve" 
            element = {<DailyDashboard/>}/>
            <Route path = "/monthly/retrieve" 
            element = {<MonthlyDashboard/>}/>
            {/* <Route path = "/yearly/retrieve" 
            element = {<YearlyDashboard/>}/> */}
            <Route path  =  "/home" 
            element  =  {<DailyDashboard/>}/>
            <Route path = "*" 
            element = {<Navigate to = "/home" replace/>}/>
        </Routes>
    );
}