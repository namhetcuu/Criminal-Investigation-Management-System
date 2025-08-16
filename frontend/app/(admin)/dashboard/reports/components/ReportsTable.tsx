'use client';

import { useState } from 'react';
import { useRouter } from 'next/navigation';
import { FiChevronLeft, FiChevronRight, FiSearch, FiFilter, FiEye, FiDownload } from 'react-icons/fi';
import { reports as rawReports } from '@/data/mockData';
import ReportFilter from './ReportFilter';
import Badge from '@/components/ui/badge';

type ReportStatus = 'Approved' | 'Pending' | 'Rejected';
type CrimeSeverity = 'Low' | 'Medium' | 'High' | 'Critical';

const ReportsTable = () => {
    const router = useRouter();
    const [filters, setFilters] = useState({
        status: 'All',
        crimeType: 'All',
        severity: 'All',
        searchQuery: '',
    });

    const [currentPage, setCurrentPage] = useState(1);
    const itemsPerPage = 8;

    const filteredReports = rawReports.filter((report) => {
        const matchStatus =
            filters.status === 'All' || report.status === filters.status;
        const matchType =
            filters.crimeType === 'All' || report.type === filters.crimeType;
        const matchSeverity =
            filters.severity === 'All' || report.severity === filters.severity;
        const matchSearch = 
            report.id.toLowerCase().includes(filters.searchQuery.toLowerCase()) ||
            report.type.toLowerCase().includes(filters.searchQuery.toLowerCase()) ||
            report.reporter.toLowerCase().includes(filters.searchQuery.toLowerCase());
            
        return matchStatus && matchType && matchSeverity && matchSearch;
    });

    // Pagination logic
    const totalPages = Math.ceil(filteredReports.length / itemsPerPage);
    const paginatedReports = filteredReports.slice(
        (currentPage - 1) * itemsPerPage,
        currentPage * itemsPerPage
    );

    const severityStyles: Record<CrimeSeverity, string> = {
        Low: 'bg-green-100 text-green-800',
        Medium: 'bg-yellow-100 text-yellow-800',
        High: 'bg-orange-100 text-orange-800',
        Critical: 'bg-red-100 text-red-800',
    };

    const handleViewReport = (reportId: string) => {
        // Navigate to the report detail page
        router.push(`/dashboard/reports/${reportId}`);
    };

    return (
        <div className="w-full p-4 md:p-6 bg-gray-50">
            <div className="mb-6 bg-white p-4 rounded-lg shadow-sm">
                <div className="flex flex-col md:flex-row justify-between items-start md:items-center gap-4 mb-4">
                    <div>
                        <h1 className="text-xl md:text-2xl font-bold text-gray-800">Crime Reports</h1>
                        <p className="text-sm text-gray-600">
                            Showing {filteredReports.length} reports
                            {filters.status !== 'All' && ` (Filtered by ${filters.status})`}
                        </p>
                    </div>
                    
                    <div className="flex flex-col md:flex-row gap-3 w-full md:w-auto">
                        <div className="relative flex-grow max-w-md">
                            <FiSearch className="absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400" />
                            <input
                                type="text"
                                placeholder="Search reports..."
                                className="pl-10 pr-4 py-2 w-full border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"
                                value={filters.searchQuery}
                                onChange={(e) => setFilters({...filters, searchQuery: e.target.value})}
                            />
                        </div>
                        <button className="flex items-center gap-2 px-4 py-2 border rounded-md bg-white hover:bg-gray-50 transition">
                            <FiDownload className="h-4 w-4" />
                            Export
                        </button>
                    </div>
                </div>

                <ReportFilter filters={filters} onChange={setFilters} />
            </div>

            {/* Table */}
            <div className="bg-white shadow-sm rounded-lg overflow-hidden">
                <div className="overflow-x-auto">
                    <table className="min-w-full divide-y divide-gray-200">
                        <thead className="bg-gray-50">
                            <tr>
                                <th scope="col" className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                                    Report ID
                                </th>
                                <th scope="col" className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                                    Crime Type
                                </th>
                                <th scope="col" className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                                    Severity
                                </th>
                                <th scope="col" className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                                    Date Reported
                                </th>
                                <th scope="col" className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                                    Reporter
                                </th>
                                <th scope="col" className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                                    Status
                                </th>
                                <th scope="col" className="px-6 py-3 text-right text-xs font-medium text-gray-500 uppercase tracking-wider">
                                    Actions
                                </th>
                            </tr>
                        </thead>
                        <tbody className="bg-white divide-y divide-gray-200">
                            {paginatedReports.length > 0 ? (
                                paginatedReports.map((report) => (
                                    <tr key={`${report.id}-${report.reporter}`} className="hover:bg-gray-50 transition">
                                        <td className="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-900">
                                            #{report.id}
                                        </td>
                                        <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900">
                                            {report.type}
                                        </td>
                                        <td className="px-6 py-4 whitespace-nowrap">
                                            <span className={`px-2.5 py-0.5 rounded-full text-xs font-medium ${severityStyles[report.severity as CrimeSeverity] || 'bg-gray-100 text-gray-800'}`}>
                                                {report.severity}
                                            </span>
                                        </td>
                                        <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                                            {new Date(report.date).toLocaleDateString()}
                                        </td>
                                        <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                                            {report.reporter}
                                        </td>
                                        <td className="px-6 py-4 whitespace-nowrap">
                                            <Badge status={report.status as ReportStatus} />
                                        </td>
                                        <td className="px-6 py-4 whitespace-nowrap text-right text-sm font-medium">
                                            <button 
                                                onClick={() => handleViewReport(report.id)}
                                                className="text-blue-600 hover:text-blue-900 mr-4 inline-flex items-center"
                                            >
                                                <FiEye className="mr-1 h-4 w-4" /> View
                                            </button>
                                            <button className="text-gray-600 hover:text-gray-900 inline-flex items-center">
                                                <FiDownload className="mr-1 h-4 w-4" /> Export
                                            </button>
                                        </td>
                                    </tr>
                                ))
                            ) : (
                                <tr>
                                    <td colSpan={7} className="px-6 py-12 text-center text-sm text-gray-500">
                                        <div className="flex flex-col items-center justify-center">
                                            <FiFilter className="h-8 w-8 text-gray-400 mb-2" />
                                            <p>No reports match your current filters</p>
                                            <button 
                                                onClick={() => setFilters({
                                                    status: 'All',
                                                    crimeType: 'All',
                                                    severity: 'All',
                                                    searchQuery: '',
                                                })}
                                                className="mt-2 text-blue-600 hover:underline"
                                            >
                                                Clear all filters
                                            </button>
                                        </div>
                                    </td>
                                </tr>
                            )}
                        </tbody>
                    </table>
                </div>

                {/* Pagination */}
                {paginatedReports.length > 0 && (
                    <div className="px-6 py-4 border-t border-gray-200 flex items-center justify-between">
                        <div className="text-sm text-gray-700">
                            Showing <span className="font-medium">{(currentPage - 1) * itemsPerPage + 1}</span> to{' '}
                            <span className="font-medium">
                                {Math.min(currentPage * itemsPerPage, filteredReports.length)}
                            </span>{' '}
                            of <span className="font-medium">{filteredReports.length}</span> reports
                        </div>
                        <div className="flex space-x-2">
                            <button
                                onClick={() => setCurrentPage(Math.max(1, currentPage - 1))}
                                disabled={currentPage === 1}
                                className={`px-3 py-1 rounded-md border ${currentPage === 1 ? 'bg-gray-100 text-gray-400 cursor-not-allowed' : 'bg-white hover:bg-gray-50'}`}
                            >
                                <FiChevronLeft className="h-4 w-4 inline" />
                            </button>
                            
                            {Array.from({ length: Math.min(5, totalPages) }, (_, i) => {
                                let pageNum;
                                if (totalPages <= 5) {
                                    pageNum = i + 1;
                                } else if (currentPage <= 3) {
                                    pageNum = i + 1;
                                } else if (currentPage >= totalPages - 2) {
                                    pageNum = totalPages - 4 + i;
                                } else {
                                    pageNum = currentPage - 2 + i;
                                }
                                
                                return (
                                    <button
                                        key={pageNum}
                                        onClick={() => setCurrentPage(pageNum)}
                                        className={`px-3 py-1 rounded-md ${currentPage === pageNum ? 'bg-blue-500 text-white' : 'bg-white border hover:bg-gray-50'}`}
                                    >
                                        {pageNum}
                                    </button>
                                );
                            })}
                            
                            <button
                                onClick={() => setCurrentPage(Math.min(totalPages, currentPage + 1))}
                                disabled={currentPage === totalPages}
                                className={`px-3 py-1 rounded-md border ${currentPage === totalPages ? 'bg-gray-100 text-gray-400 cursor-not-allowed' : 'bg-white hover:bg-gray-50'}`}
                            >
                                <FiChevronRight className="h-4 w-4 inline" />
                            </button>
                        </div>
                    </div>
                )}
            </div>
        </div>
    );
};

export default ReportsTable;