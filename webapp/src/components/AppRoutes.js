import {Routes, Route, Navigate} from "react-router-dom";
import DailyForm from "../forms/DailyForm";
import DailyDashboard from "../dashboards/DailyDashboard";
import MonthlyDashboard from "../dashboards/MonthlyDashboard";
import YearlyDashboard from "../dashboards/YearlyDashboard";
import AccountDashboard from "../dashboards/AccountDashboard";
import AccountForm from "../forms/AccountForm";

export default function AppRoutes 
() {
    return (
        <Routes>
            <Route path = 
            "/daily/form" 
            element = 
            {<DailyForm/>}/>
            <Route path = 
            "/account_info/form" 
            element = 
            {<AccountForm/>}/>
            <Route
            path = 
            "/account_info/form/:id"
            element = 
            {<AccountForm/>}/>
            <Route path = 
            "/daily/retrieve" 
            element = 
            {<DailyDashboard/>}/>
            <Route path = 
            "/monthly/retrieve" 
            element = 
            {<MonthlyDashboard/>}/>
            <Route path = 
            "/yearly/retrieve" 
            element = 
            {<YearlyDashboard/>}/>
            <Route path = 
            "/account_info/retrieve" 
            element = 
            {<AccountDashboard/>}/>
            <Route path  = 
            "/home" 
            element = 
            {<DailyDashboard/>}/>
            <Route path = 
            "*" 
            element = 
            {<Navigate to = 
            "/home" 
            replace/>}/>
        </Routes>
    );
}