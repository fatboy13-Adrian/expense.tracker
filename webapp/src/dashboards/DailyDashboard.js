import {useEffect, useState} from "react";
import axios from "axios";
import {useNavigate} from "react-router-dom";
import DailyTable from "../components/tables/DailyTable";
import InputDate from "../components/inputs/InputDate";
import "../styles/Dashboard.css";

//API configuration
const API_URL = 
"http://localhost:8080/daily";
const PAGE_SIZE = 10;

//Return current month in YYYY-MM format
function getCurrentMonth () {
    const today = 
    new Date();
    const year = 
    today
    .getFullYear();
    const month = 
    String(today
    .getMonth() + 1)
    .padStart(2, "0");
    return `${year}-${month}`;
}

export default function DailyDashboard () {
    const [daily, setDaily] = 
    useState([]);
    const [selectedMonth, setSelectedMonth] = 
    useState("");
    const [page, setPage] = 
    useState(1);
    const [totalPages, setTotalPages] = 
    useState(0);
    const [error, setError] = 
    useState("");
    const [loading, setLoading] = 
    useState(true);
    const navigate = 
    useNavigate();

    //Fetch requested page of daily expense records
    useEffect(() => {
        let isMounted = true;
        const fetchDaily = async () => {
            setLoading(true);
            setError("");
            try {
                const response = 
                await axios
                .get(API_URL, {
                    params: {
                        //Existing backend expects one-based page numbers
                        page,
                        size: PAGE_SIZE
                    }
                });

                const data = 
                response
                .data;
                const records = 
                data?.content;

                if (!Array.isArray(records)) {
                    throw new Error(
                    "Unexpected API response: expense records were not found.");
                }

                if (isMounted) {
                    setDaily(records);
                    setTotalPages(data
                    .totalPages ?? 0);
                }
            } catch (err) {
                console.error(
                "Failed to retrieve daily expense records:",
                err);

                if (isMounted) {
                    setDaily([]);
                    setTotalPages(0);
                    setError(
                    err.response?.data?.message ||
                    err.message ||
                    "Failed to retrieve daily expense records.");
                }
            } finally {
                if (isMounted) 
                setLoading(false);
            }
        };

        fetchDaily();

        return () => {
            isMounted = false;
        };
    }, [page]);

    //Filter current page by month & sort newest first
    const filteredDaily = 
    daily
    .filter((record) => (
        typeof record.date === 
        "string" &&
        /^\d{4}-\d{2}-\d{2}$/
        .test(record.date)
    )).filter((record) => (
        selectedMonth === "" ||
        record
        .date
        .startsWith(selectedMonth)
    )).sort((a, b) => 
    b.date
    .localeCompare(a.date));

    //Change selected month & restart pagination
    const handleMonthChange = 
    (event) => {
        setSelectedMonth(event.target.value);
        setPage(1);
    };

    //Return to current month
    const handleResetToCurrentMonth = 
    () => {
        setSelectedMonth(getCurrentMonth());
        setPage(1);
    };

    //Display records without month filter
    const handleViewAllMonths = 
    () => {
        setSelectedMonth("");
        setPage(1);
    };

    const handlePreviousPage = 
    () => {
        setPage((currentPage) => 
        Math
        .max(1, 
        currentPage - 1));
    };

    const handleNextPage = 
    () => {
        setPage((currentPage) =>
        Math
        .min(totalPages, 
        currentPage + 1));
    };

    return (
        <div className = 
        "dashboard-center">
            <h1>Daily Expense Records</h1>

            <div className = 
            "filter-section">
                <InputDate
                    type = "month"
                    value = {selectedMonth}
                    onChange = {handleMonthChange}
                    className = "input"
                    max = {getCurrentMonth()}
                />
            </div>

            {loading ? (
                <p>Loading daily expense records...</p>
            ) : error ? (
                <div className = 
                "error-message" 
                role = "alert">
                    {error}
                </div>
            ) : (
                <>
                    <div className = 
                    "allTables-table-wrapper">
                        <DailyTable dailyExp = 
                        {filteredDaily}/>
                    </div>

                    <div className = "pagination-controls">
                        <button
                            type = "button"
                            className = "custom-btn"
                            onClick = {handlePreviousPage}
                            disabled = {page <= 1 || loading}>
                            Previous
                        </button>

                        <span>
                            Page {totalPages === 0 ? 
                            0 : page} of {totalPages}
                        </span>

                        <button
                            type = "button"
                            className = "custom-btn"
                            onClick = {handleNextPage}
                            disabled = {page >= 
                            totalPages || 
                            loading}>
                            Next
                        </button>
                    </div>

                    <div className = "buttons-center">
                        <button
                            type = "button"
                            className = "custom-btn"
                            onClick={() => 
                            navigate("/daily/form")}>
                            New / Edit
                        </button>

                        <button
                            type = "button"
                            className = "custom-btn"
                            onClick = {handleViewAllMonths}>
                            View All
                        </button>

                        <button
                            type = "button"
                            className = "custom-btn"
                            onClick = {handleResetToCurrentMonth}>
                            Reset
                        </button>

                        <button 
                            className = "custom-btn" 
                            onClick = {() => navigate("/monthly/retrieve")}>
                            Monthly
                        </button>
                    </div>
                </>
            )}
        </div>
    );
}