'use client'
import React, { useState } from "react";
import { FiPlus, FiEye, FiEdit2, FiChevronLeft, FiChevronRight, FiSearch } from "react-icons/fi";
import { useRouter } from "next/navigation";

type CaseStatus = "Open" | "In Progress" | "Closed";
type Case = {
  id: number;
  title: string;
  status: CaseStatus;
  assignedTo: string;
  createdAt: string;
  priority?: "Low" | "Medium" | "High";
};

const CasesPage = () => {
  const router = useRouter();
  const [searchTerm, setSearchTerm] = useState("");
  const [currentPage, setCurrentPage] = useState(1);
  const casesPerPage = 5;

  const cases: Case[] = [
    {
      id: 1,
      title: "Unauthorized Access Attempt",
      status: "Open",
      assignedTo: "John Doe",
      createdAt: "2024-06-01",
      priority: "High",
    },
    {
      id: 2,
      title: "Financial Fraud Investigation",
      status: "In Progress",
      assignedTo: "Jane Smith",
      createdAt: "2024-06-02",
      priority: "Medium",
    },
    {
      id: 3,
      title: "Vandalism Report",
      status: "Closed",
      assignedTo: "Alice Johnson",
      createdAt: "2024-06-03",
      priority: "Low",
    },
    {
      id: 4,
      title: "Missing Property Case",
      status: "Open",
      assignedTo: "Robert Brown",
      createdAt: "2024-06-05",
      priority: "Medium",
    },
    {
      id: 5,
      title: "Cyber Security Breach",
      status: "In Progress",
      assignedTo: "John Doe",
      createdAt: "2024-06-07",
      priority: "High",
    },
    {
      id: 6,
      title: "Harassment Complaint",
      status: "Open",
      assignedTo: "Sarah Wilson",
      createdAt: "2024-06-08",
      priority: "High",
    },
  ];

  const filteredCases = cases.filter((c) =>
    c.title.toLowerCase().includes(searchTerm.toLowerCase()) ||
    c.assignedTo.toLowerCase().includes(searchTerm.toLowerCase())
  );

  // Pagination logic
  const indexOfLastCase = currentPage * casesPerPage;
  const indexOfFirstCase = indexOfLastCase - casesPerPage;
  const currentCases = filteredCases.slice(indexOfFirstCase, indexOfLastCase);
  const totalPages = Math.ceil(filteredCases.length / casesPerPage);

  const statusStyles = {
    Open: "bg-green-100 text-green-800",
    "In Progress": "bg-blue-100 text-blue-800",
    Closed: "bg-gray-200 text-gray-700",
  };

  const priorityStyles = {
    High: "bg-red-100 text-red-800",
    Medium: "bg-yellow-100 text-yellow-800",
    Low: "bg-green-100 text-green-800",
  };

  const handleViewCase = (caseId: number) => {
    router.push(`/cases/${caseId}`);
  };

  return (
    <main className="p-4 md:p-8 bg-gray-50">
      <div className="max-w-7xl mx-auto">
        <div className="flex flex-col md:flex-row justify-between items-start md:items-center gap-4 mb-6">
          <div>
            <h1 className="text-2xl md:text-3xl font-bold text-gray-800">Case Management</h1>
            <p className="text-gray-600">Track and manage all active cases</p>
          </div>
          <div className="flex gap-3 w-full md:w-auto">
            <button
              className="flex items-center gap-2 bg-blue-600 text-white px-4 py-2 rounded-md hover:bg-blue-700 transition"
            >
              <FiPlus /> New Case
            </button>
          </div>
        </div>

        <div className="bg-white rounded-xl shadow-sm border border-gray-200 overflow-hidden mb-6">
          <div className="p-4 border-b border-gray-200">
            <div className="relative max-w-md">
              <FiSearch className="absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400" />
              <input
                type="text"
                placeholder="Search cases..."
                className="pl-10 pr-4 py-2 w-full border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"
                value={searchTerm}
                onChange={(e) => {
                  setSearchTerm(e.target.value);
                  setCurrentPage(1);
                }}
              />
            </div>
          </div>

          <div className="overflow-x-auto">
            <table className="min-w-full divide-y divide-gray-200">
              <thead className="bg-gray-50">
                <tr>
                  <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                    Case ID
                  </th>
                  <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                    Title
                  </th>
                  <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                    Priority
                  </th>
                  <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                    Status
                  </th>
                  <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                    Assigned To
                  </th>
                  <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                    Created At
                  </th>
                  <th className="px-6 py-3 text-right text-xs font-medium text-gray-500 uppercase tracking-wider">
                    Actions
                  </th>
                </tr>
              </thead>
              <tbody className="bg-white divide-y divide-gray-200">
                {currentCases.length > 0 ? (
                  currentCases.map((c) => (
                    <tr key={c.id} className="hover:bg-gray-50 transition">
                      <td className="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-900">
                        #{c.id}
                      </td>
                      <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900">
                        {c.title}
                      </td>
                      <td className="px-6 py-4 whitespace-nowrap">
                        {c.priority && (
                          <span className={`px-2.5 py-0.5 rounded-full text-xs font-medium ${priorityStyles[c.priority]}`}>
                            {c.priority}
                          </span>
                        )}
                      </td>
                      <td className="px-6 py-4 whitespace-nowrap">
                        <span className={`px-2.5 py-0.5 rounded-full text-xs font-medium ${statusStyles[c.status]}`}>
                          {c.status}
                        </span>
                      </td>
                      <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                        {c.assignedTo}
                      </td>
                      <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                        {new Date(c.createdAt).toLocaleDateString()}
                      </td>
                      <td className="px-6 py-4 whitespace-nowrap text-right text-sm font-medium">
                        <div className="flex justify-end gap-2">
                          <button
                            onClick={() => handleViewCase(c.id)}
                            className="text-blue-600 hover:text-blue-900 p-1 rounded hover:bg-blue-50 transition"
                          >
                            <FiEye className="h-5 w-5" />
                          </button>
                          <button className="text-gray-600 hover:text-gray-900 p-1 rounded hover:bg-gray-50 transition">
                            <FiEdit2 className="h-5 w-5" />
                          </button>
                        </div>
                      </td>
                    </tr>
                  ))
                ) : (
                  <tr>
                    <td colSpan={7} className="px-6 py-12 text-center text-sm text-gray-500">
                      <div className="flex flex-col items-center justify-center">
                        <FiSearch className="h-8 w-8 text-gray-400 mb-2" />
                        <p>No cases match your search</p>
                      </div>
                    </td>
                  </tr>
                )}
              </tbody>
            </table>
          </div>

          {currentCases.length > 0 && (
            <div className="px-6 py-4 border-t border-gray-200 flex items-center justify-between">
              <div className="text-sm text-gray-700">
                Showing <span className="font-medium">{indexOfFirstCase + 1}</span> to{' '}
                <span className="font-medium">
                  {Math.min(indexOfLastCase, filteredCases.length)}
                </span>{' '}
                of <span className="font-medium">{filteredCases.length}</span> cases
              </div>
              <div className="flex space-x-2">
                <button
                  onClick={() => setCurrentPage(Math.max(1, currentPage - 1))}
                  disabled={currentPage === 1}
                  className={`px-3 py-1 rounded-md border ${currentPage === 1 ? 'bg-gray-100 text-gray-400 cursor-not-allowed' : 'bg-white hover:bg-gray-50'}`}
                >
                  <FiChevronLeft className="h-4 w-4 inline" />
                </button>
                <span className="px-3 py-1 rounded-md bg-blue-500 text-white">
                  {currentPage}
                </span>
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
    </main>
  );
};

export default CasesPage;