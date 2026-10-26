import {useEffect, useState} from "react";
import axios from "axios";
import {useNavigate} from "react-router-dom";
import DailyTable from "../components/tables/DailyTable";
import InputDate from "../components/inputs/InputDate";
import "../styles/Dashboard.css";

//API Configuration
const API_URL = 
"http://localhost:8080/daily";
const PAGE_SIZE = 10;

//Return current month
function getCurrentMonth () {
    const today = 
    new Date();
    const year = 
    today
    .getFullYear();
    const month = 
    String(
    today
    .getMonth() + 1)
    .padStart(2, "0");
    return `${year}-${month}`;
}

//Check whether a value is a valid yyyy-MM month
function isValidMonth(value) {
    return /^\d{4}-(0[1-9]|1[0-2])$/
    .test(value);
}

export default function DailyDashboard 
() {
    //Daily expense records for currently fetched page
    const [daily, setDaily] = 
    useState([]);

    //Initialize month selector to current month
    const [selectedMonth, setSelectedMonth] = 
    useState(getCurrentMonth());

    //Pagination state
    const [page, setPage] = 
    useState(1);
    const [totalPages, setTotalPages] = 
    useState(0);

    //Loading & error state
    const [error, setError] = 
    useState("");
    const [loading, setLoading] = 
    useState(true);

    //Prevent duplicate report downloads
    const [downloading, setDownloading] = 
    useState(false);
    const navigate = 
    useNavigate();

    //Fetch daily records
    useEffect(() => {
        let isMounted = 
        true;

        const fetchDaily = 
        async () => {
            setLoading(true);
            setError("");

            try {
                const response = 
                await axios
                .get(API_URL, {
                    params: {
                        //Backend expects one-based page numbers
                        page,
                        size: PAGE_SIZE}
                });

                const data = 
                response.data;
                const records = 
                data?.content;

                //Validate response structure
                if (!Array.isArray(records)) {
                    throw new Error(
                    "Unexpected API response: expense records were not found.");
                }

                if (isMounted) {
                    setDaily(records);
                    setTotalPages(data.totalPages ?? 0);
                }
            } catch (err) {
                console
                .error(
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
            isMounted = 
            false;
        };
    }, [page]);

    //Filter & sort daily records
    const filteredDaily = 
    daily
    .filter((record) => (
            typeof record
            .date === 
            "string" &&
            /^\d{4}-\d{2}-\d{2}$/
            .test(record
            .date)
    ))
    .filter((record) => (
        selectedMonth === "" ||
        record
        .date
        .startsWith(selectedMonth)
    ))
    .sort((a, b) => (
        b.date
        .localeCompare(a.date)));

    //Update selected month
    const handleMonthChange = 
    (event) => {
        setSelectedMonth(event.target.value);
        setPage(1);
    };

    //Reset selector to current month
    const handleResetToCurrentMonth = 
    () => {
        setSelectedMonth(getCurrentMonth());
        setPage(1);
    };

    //Display records without a month filter
    const handleViewAllMonths = 
    () => {
        setSelectedMonth("");
        setPage(1);
    };

    //Pagination Handlers
    const handlePreviousPage = 
    () => {
        setPage((currentPage) => (
            Math
            .max(1, 
            currentPage - 1)));
    };

    const handleNextPage = 
    () => {
        setPage((currentPage) => (
            Math
            .min(totalPages, 
            currentPage + 1)));
    };

    //Download monthly expense reports
    const downloadExcel = 
        async () => {
        //Ensure a month has been selected
        if (!selectedMonth) {
            alert(
            "Please select a month before downloading report.");
            return;
        }

        //Validate selected month
        if (!isValidMonth(selectedMonth)) {
            alert(
            "Invalid month format. Please select a valid month.");
            return;
        }

        setDownloading(true);

        try {
            //Construct backend endpoint using selected month
            const endpoint =
            `${API_URL}/export/${selectedMonth}`;

            console
            .log(
            "Downloading monthly report:",
            endpoint);

            //Send GET request to Spring Boot
            const response = 
            await fetch(endpoint, {
                method: "GET"
            });

            //Handle errors returned by backend
            if (!response.ok) {
                let errorMessage;

                try {
                    const errorBody = 
                    await response
                    .json();

                    errorMessage =
                    errorBody.message ||
                    errorBody.error ||
                    response.statusText;
                } catch {
                    errorMessage = 
                    response
                    .statusText;
                }

                throw new Error(
                `HTTP ${response.status}: ${errorMessage}`);
            }

            //Convert response into an Excel file
            const blob = 
            await response
            .blob();

            //Ensure downloaded file contains data
            if (blob.size === 0) {
                throw new Error(
                "The downloaded Excel file is empty.");
            }

            //Convert yyyy-MM into a full month name & year
            const [year, month] = 
            selectedMonth
            .split("-");

            const monthYear = 
            new Date(
            Number(year),
            Number(month) - 1
            ).toLocaleDateString("en-SG", {
                month: "long",
                year: "numeric"
            });

            //Create a temporary URL for Excel file
            const url = 
            window
            .URL
            .createObjectURL(blob);

            //Configure download link
            const link = 
            document
            .createElement("a");

            link.href = url;
            link.download =
            `Expense Records for ${monthYear}.xlsx`;

            //Trigger browser download
            document
            .body
            .appendChild(link);
            link.click();
            link.remove();

            //Release temporary URL after download starts
            window.setTimeout(() => {
                window
                .URL
                .revokeObjectURL(url);
            }, 1000);

            console
            .log(
            `Excel report for ${monthYear} downloaded successfully.`);
        } catch (err) {
            console
            .error(
            "Error downloading Excel report:",
            err);

            alert(
            `Unable to download expense report.\n${err.message}`);
        } finally {
            setDownloading(false);
        }
    };

    //Render Dashboard
    return (
        <div className = 
        "dashboard-center">
            <h1>Daily Expense Records</h1>
            {/* Month selector */}
            <div className = 
            "filter-section">
                <InputDate
                    name = "selectedMonth"
                    label = "Month:"
                    type = "month"
                    value = {selectedMonth}
                    onChange = {handleMonthChange}
                    className = "input"
                    max = {getCurrentMonth()}/>

            </div>

            {/* Loading & error messages */}
            {loading ? (
                <p>Loading daily expense records...</p>
            ) : error ? (
                <div
                    className = 
                    "error-message"
                    role = 
                    "alert">
                    {error}
                </div>
            ) : (

                <>
                    {/* Daily expense table */}
                    <div className = 
                    "allTables-table-wrapper">
                        <DailyTable
                        dailyExp = 
                        {filteredDaily}/>
                    </div>

                    {/* Pagination */}
                    <div className = 
                    "pagination-controls">
                        <button
                            type = "button"
                            className = "custom-btn"
                            onClick = {handlePreviousPage}
                            disabled = {page <= 1 || loading}>
                            Previous
                        </button>

                        <span>
                            Page {totalPages === 0 ? 0 : page}
                            {" "}of{" "}
                            {totalPages}
                        </span>

                        <button
                            type = "button"
                            className = "custom-btn"
                            onClick = {handleNextPage}
                            disabled = {
                            page >= totalPages ||
                            loading}>
                            Next
                        </button>

                    </div>

                    {/* Dashboard action buttons */}
                    <div className = "buttons-center">
                        <button
                            type = "button"
                            className = "custom-btn"
                            onClick = {() => 
                            navigate("/daily/form")}>
                            New / Edit
                        </button>

                        <button
                            type = "button"
                            className = "custom-btn"
                            onClick = 
                            {handleViewAllMonths}>
                            View All
                        </button>

                        <button
                            type = "button"
                            className = "custom-btn"
                            onClick = 
                            {handleResetToCurrentMonth}>
                            Reset
                        </button>

                        <button
                            type = "button"
                            className = "custom-btn"
                            onClick = 
                            {downloadExcel}
                            disabled = {
                            !selectedMonth ||
                            downloading ||
                            loading}>
                            {downloading
                            ? "Downloading..."
                            : "Download"}
                        </button>

                        <button
                            type = "button"
                            className = "custom-btn"
                            onClick = {() => 
                            navigate("/monthly/retrieve")}>
                            Monthly
                        </button>

                        <button
                            type = "button"
                            className = "custom-btn"
                            onClick = {() => 
                            navigate("/yearly/retrieve")}>
                            Yearly
                        </button>
                    </div>
                </>
            )}
        </div>
    );
}