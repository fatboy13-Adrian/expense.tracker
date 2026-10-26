import {useEffect, useState} from "react";
import axios from "axios";
import {useNavigate} from "react-router-dom";
import YearlyTable from "../components/tables/YearlyTable";
import "../styles/Dashboard.css";

//API Configuration
const API_URL = 
"http://localhost:8080/yearly";
const PAGE_SIZE = 10;

export default function YearlyDashboard 
() {
    //Store yearly expense records for current page
    const [yearly, setYearly] = 
    useState([]);

    //Store selected page number
    const [page, setPage] = 
    useState(1);

    //Store total number of pages returned by backend
    const [totalPages, setTotalPages] = 
    useState(0);

    //Store errors & loading status
    const [error, setError] = 
    useState("");
    const [loading, setLoading] = 
    useState(true);
    const navigate = 
    useNavigate();

    //Fetch yearly records
    useEffect(() => {
        let isMounted = 
        true;

        const fetchYearly = 
        async () => {
            setLoading(true);
            setError("");

            try {
                //Fetch only requested page
                const response = 
                await axios
                .get(API_URL, {
                    params: {
                        page: page - 1,
                        size: PAGE_SIZE
                    }
                });

                const data = 
                response
                .data;

                //Validate paginated response
                if (!Array.isArray(data?.content)) {
                    throw new Error(
                    "Unexpected API response: yearly expense records were not found.");
                }

                //Sort current page by year, newest first
                const sortedRecords = 
                [...data.content]
                .sort((a, b) =>
                    String(b.year ?? "")
                    .localeCompare(
                        String(a.year ?? ""),
                        undefined, { 
                            numeric: true 
                        }
                    )
                );

                if (isMounted) {
                    setYearly(sortedRecords);
                    setTotalPages(Number(data
                    .totalPages ?? 0));
                }
            } catch (err) {
                console
                .error(
                "Failed to retrieve yearly expense records:",
                err);

                if (isMounted) {
                    setYearly([]);
                    setTotalPages(0);
                    setError(
                    err.response?.data?.message ||
                    err.message ||
                    "Failed to retrieve yearly expense records.");
                }
            } finally {
                if (isMounted) 
                setLoading(false);
            }
        };

        fetchYearly();

        //Prevent state updates after unmounting
        return () => {
            isMounted = 
            false;
        };
    }, [page]);

    const handlePreviousPage = 
    () => {
        setPage((currentPage) =>
        Math
        .max(1, 
        currentPage - 1)
        );
    };

    const handleNextPage = 
    () => {
        setPage((currentPage) =>
        Math
        .min(totalPages, 
        currentPage + 1)
        );
    };

    const downloadExcel = 
    async () => {
        try {
            const response = 
            await fetch("http://localhost:8080/yearly/export");

            if (!response.ok) 
            throw new Error("Failed to download file");

            const blob = 
            await response
            .blob();
            const url = 
            window
            .URL
            .createObjectURL(blob);

            const link = 
            document
            .createElement("a");
            link.href = url;
            link.download = 
            "Expense Records For All Years.xlsx";

            document.body
            .appendChild(link);
            link.click();
            link.remove();

            window
            .URL
            .revokeObjectURL(url);
        } catch (err) {
            console
            .error(
            "Error downloading expense report:",
            error);
            alert(error.message || 
            "Unable to download expense report.");
        }
    };

    return (
        <div className = 
        "dashboard-center">
            <h1>Yearly Expense Records</h1>

            {loading ? (
                <p>Loading yearly expense records...</p>
            ) : error ? (
                <div
                className = 
                "error-message"
                role = "alert">
                    {error}
                </div>
            ) : (
                <>
                    {yearly
                    .length === 0 ? (
                        <p>No yearly expense records found.</p>
                    ) : (
                        <div className = 
                        "allTables-table-wrapper">
                            <YearlyTable 
                            yearlyExp = 
                            {yearly}/>
                        </div>
                    )}

                    <div className = 
                    "pagination-controls">
                        <button
                            type = "button"
                            className = "custom-btn"
                            onClick = {handlePreviousPage}
                            disabled = {page <= 1 || 
                            loading}>
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
                            disabled={
                            totalPages === 0 ||
                            page >= totalPages ||
                            loading}>
                            Next
                        </button>
                    </div>

                    <div className = 
                    "buttons-center">
                        <button
                            type = "button"
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
                            navigate("/monthly/retrieve")}>
                            Monthly
                        </button>

                        <button
                            type = "button"
                            className = "custom-btn"
                            onClick = {() => 
                            navigate("/yearly/retrieveAll")}>
                            Yearly
                        </button>
                    </div>
                </>
            )}
        </div>
    );
}