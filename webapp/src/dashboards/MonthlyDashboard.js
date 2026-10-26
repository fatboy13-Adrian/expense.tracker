import {useEffect, useState} from "react";
import axios from "axios";
import {useNavigate} from "react-router-dom";
import MonthlyTable from "../components/tables/MonthlyTable";
import InputMonth from "../components/inputs/InputMonth";
import "../styles/Dashboard.css";

//API configuration
const API_URL = 
"http://localhost:8080/monthly";
const PAGE_SIZE = 10;

//Return  current year
function getCurrentYear () {
    return new Date()
    .getFullYear()
    .toString();
}

export default function MonthlyDashboard () {
    const [monthly, setMonthly] = 
    useState([]);
    const [selectedYear, setSelectedYear] = 
    useState("");
    const [page, setPage] = 
    useState(1);
    const [error, setError] = 
    useState("");
    const [loading, setLoading] = 
    useState(true);
    const navigate = 
    useNavigate();

    //Fetch all monthly expense records when page loads
    useEffect(() => {
        let isMounted = 
        true;

        const fetchMonthly = 
        async () => {
            setLoading(true);
            setError("");
            try {
                //Fetch 1st page to determine total pages
                const firstResponse = 
                await axios.get(API_URL, {
                    params: {
                        page: 1,
                        size: PAGE_SIZE
                    }
                });

                const firstData = 
                firstResponse.data;

                if (!Array.isArray(firstData?.content)) {
                    throw new Error(
                    "Unexpected API response: expense records were not found.");
                }

                //Start with records from 1st page
                let allRecords = 
                [...firstData.content];

                const totalPagesFromAPI = 
                firstData
                .totalPages ?? 0;

                //Fetch remaining pages, if any
                if (totalPagesFromAPI > 1) {
                    const remainingRequests = 
                    [];

                    for (let currentPage = 2;
                    currentPage <= totalPagesFromAPI;
                    currentPage++) {
                        remainingRequests
                        .push(
                        axios
                        .get(API_URL, {
                            params: {
                                page: currentPage,
                                size: PAGE_SIZE
                            }
                        }));
                    }

                    const remainingResponses = 
                    await Promise
                    .all(
                    remainingRequests);

                    remainingResponses
                    .forEach((response) => {
                        const records = 
                        response.data?.content;

                        if (Array.isArray(records)) {
                            allRecords = 
                            allRecords
                            .concat(records);
                        }
                    });
                }

                //Sort all records by month, newest first
                allRecords
                .sort((a, b) =>
                String(b.month ?? "")
                .localeCompare(
                String(a.month ?? "")));

                if (isMounted) {
                    setMonthly(allRecords);

                    //Start by displaying all years
                    setSelectedYear("");
                    setPage(1);
                }
            } catch (err) {
                console
                .error(
                "Failed to retrieve monthly expense records:",
                err);

                if (isMounted) {
                    setMonthly([]);
                    setError(
                    err.response?.data?.message ||
                    err.message ||
                    "Failed to retrieve monthly expense records.");
                }
            } finally {
                if (isMounted)
                setLoading(false);
            }
        };

        fetchMonthly();

        return () => {
            isMounted = 
            false;
        };
    }, []);

    //Filter records by selected year
    const filteredMonthly = 
    monthly.filter((record) => {
        const recordMonth = 
        String(record
        .month ?? "");

        return (
            recordMonth !== "" &&
            (selectedYear === "" ||
            recordMonth
            .startsWith(selectedYear))
        );
    });

    //Calculate number of pages after filtering
    const totalPages = 
    Math
    .ceil(filteredMonthly.length / PAGE_SIZE);

    //Display only records belonging to current page
    const paginatedMonthly = 
    filteredMonthly
    .slice(
    (page - 1) * PAGE_SIZE,
    page * PAGE_SIZE);

    //Change selected year & return to page 1
    const handleYearChange = 
    (event) => {
        setSelectedYear(event.target.value);
        setPage(1);
    };

    //Display all years, starting from page 1
    const handleReset = 
    () => {
        setSelectedYear(getCurrentYear());
        setPage(1);
    };

    //Display all years, starting from page 1
    const handleViewAllYears = 
    () => {
        setSelectedYear("");
        setPage(1);
    };

    //Navigate to previous page
    const handlePreviousPage = 
    () => {
        setPage((currentPage) =>
        Math
        .max(1, 
        currentPage - 1));
    };

    //Navigate to next page
    const handleNextPage = 
    () => {
        setPage((currentPage) =>
        Math
        .min(totalPages, 
        currentPage + 1));
    };

    //Download yearly excel report
    const downloadExcel = 
    async () => {
        try {
            //Get selected year & use current year if no year selected
            const year = 
            selectedYear
            ? selectedYear
            .substring(0, 4)
            : getCurrentYear();

            //Request yearly report
            const response = 
            await fetch(
                `${API_URL}/export/${year}`,
                {
                    method: "GET"
                }
            );

            //Check whether request was successful
            if (!response.ok) {
                const errorMessage = 
                await response
                .text();
                throw new Error(
                    `Download failed (${response.status}): ${errorMessage}`);
            }

            //Convert response into a Blob
            const blob = 
            await response
            .blob();

            //Create temporary download URL
            const url = 
            window
            .URL
            .createObjectURL(blob);

            //Create download link
            const link = 
            document
            .createElement("a");
            link.href = 
            url;
            link.download = 
            `Expense Records For Year ${year}.xlsx`;

            //Trigger download
            document.body
            .appendChild(link);
            link
            .click();
            link
            .remove();

            //Release temporary URL
            setTimeout(() => {
                window
                .URL
                .revokeObjectURL(url);
            }, 1000);
        } catch (error) {
            console
            .error(
            "Error downloading yearly expense report:",
            error);
            alert(error.message || 
            "Unable to download yearly expense report.");
        }
    };

    return (
        <div className = 
        "dashboard-center">
            <h1>Monthly Expense Records</h1>
            <div className = 
            "filter-section">
                <InputMonth
                    type = "year"
                    value = {selectedYear}
                    onChange = {handleYearChange}
                    className = "input"
                    max = {getCurrentYear()}/>
            </div>

            {loading ? (
                <p>Loading monthly expense records...</p>
            ) : error ? (
                <div className = 
                "error-message" 
                role = 
                "alert">
                    {error}
                </div>
            ) : (
                <>
                    <div className = 
                    "allTables-table-wrapper">
                        <MonthlyTable
                        monthlyExp = 
                        {paginatedMonthly}/>
                    </div>

                    <div className = 
                    "pagination-controls">
                        <button
                            type = "button"
                            className = "custom-btn"
                            onClick = {handlePreviousPage}
                            disabled = {page <= 1}>
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
                            disabled = {
                                totalPages === 0 ||
                                page >= totalPages
                            }>
                            Next
                        </button>
                    </div>

                    <div className = 
                    "buttons-center">
                        <button
                            type = "button"
                            className = "custom-btn"
                            onClick = {handleViewAllYears}>
                            View All
                        </button>

                        <button
                            type = "button"
                            className = "custom-btn"
                            onClick = {handleReset}>
                            Reset
                        </button>

                        <button 
                            className = "custom-btn" 
                            onClick = {downloadExcel}>
                            Download
                        </button>

                        <button
                            type = "button"
                            className = "custom-btn"
                            onClick = 
                            {() => navigate("/home")}>
                            Home
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