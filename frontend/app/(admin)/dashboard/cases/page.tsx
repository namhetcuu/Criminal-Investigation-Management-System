'use client'
import React from "react";
import { FiPlus, FiEye, FiEdit2, FiSearch, FiFilter } from "react-icons/fi";

const cases = [
  {
    id: 1,
    title: "Case 001",
    status: "Open",
    assignedTo: "John Doe",
    createdAt: "2024-06-01",
    priority: "High",
    description: "Customer reported payment processing issues"
  },
  {
    id: 2,
    title: "Case 002",
    status: "In Progress",
    assignedTo: "Jane Smith",
    createdAt: "2024-06-02",
    priority: "Medium",
    description: "Login authentication problems"
  },
  {
    id: 3,
    title: "Case 003",
    status: "Closed",
    assignedTo: "Alice Johnson",
    createdAt: "2024-06-03",
    priority: "Low",
    description: "UI improvement suggestions"
  },
  {
    id: 4,
    title: "Case 004",
    status: "Open",
    assignedTo: "Robert Brown",
    createdAt: "2024-06-05",
    priority: "Critical",
    description: "System outage in EU region"
  },
  {
    id: 5,
    title: "Case 005",
    status: "In Progress",
    assignedTo: "John Doe",
    createdAt: "2024-06-07",
    priority: "Medium",
    description: "API response time optimization"
  }
];

const statusStyles = {
  Open: "bg-green-100 text-green-800",
  "In Progress": "bg-blue-100 text-blue-800",
  Closed: "bg-gray-200 text-gray-700"
};

const priorityStyles = {
  Critical: "bg-red-100 text-red-800",
  High: "bg-orange-100 text-orange-800",
  Medium: "bg-yellow-100 text-yellow-800",
  Low: "bg-gray-100 text-gray-800"
};

export default function CasesPage() {
  const [viewMode, setViewMode] = React.useState("table"); // 'table' or 'card'
  const [searchTerm, setSearchTerm] = React.useState("");

  const filteredCases = cases.filter(caseItem =>
    caseItem.title.toLowerCase().includes(searchTerm.toLowerCase()) ||
    caseItem.assignedTo.toLowerCase().includes(searchTerm.toLowerCase()) ||
    caseItem.description.toLowerCase().includes(searchTerm.toLowerCase())
  );

  return (
    <main className="p-4 md:p-8">
      <div className="max-w-7xl mx-auto">
        <div className="flex flex-col md:flex-row items-start md:items-center justify-between mb-6 gap-4">
          <div>
            <h1 className="text-2xl md:text-3xl font-bold text-gray-800">Case Management</h1>
            <p className="text-gray-600">Track and manage all customer support cases</p>
          </div>
          <div className="flex gap-2 w-full md:w-auto">
            <button
              onClick={() => setViewMode(viewMode === "table" ? "card" : "table")}
              className="px-4 py-2 border rounded-md bg-white hover:bg-gray-50 transition"
            >
              {viewMode === "table" ? "Card View" : "Table View"}
            </button>
            <button className="flex items-center gap-2 bg-blue-600 text-white px-4 py-2 rounded-md hover:bg-blue-700 transition">
              <FiPlus /> New Case
            </button>
          </div>
        </div>

        <div className="bg-white rounded-xl shadow-sm border border-gray-200 overflow-hidden mb-6">
          <div className="p-4 border-b border-gray-200 flex flex-col md:flex-row gap-4">
            <div className="relative flex-grow">
              <FiSearch className="absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400" />
              <input
                type="text"
                placeholder="Search cases..."
                className="pl-10 pr-4 py-2 w-full border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"
                value={searchTerm}
                onChange={(e) => setSearchTerm(e.target.value)}
              />
            </div>
            <div className="flex gap-2">
              <button className="flex items-center gap-2 px-4 py-2 border rounded-md bg-white hover:bg-gray-50 transition">
                <FiFilter /> Filter
              </button>
              <select className="px-4 py-2 border rounded-md bg-white hover:bg-gray-50 transition">
                <option>All Status</option>
                <option>Open</option>
                <option>In Progress</option>
                <option>Closed</option>
              </select>
            </div>
          </div>

          {viewMode === "table" ? (
            <div className="overflow-x-auto">
              <table className="min-w-full divide-y divide-gray-200">
                <thead className="bg-gray-50">
                  <tr>
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Case ID</th>
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Title</th>
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Status</th>
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Priority</th>
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Assigned To</th>
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Created</th>
                    <th className="px-6 py-3 text-right text-xs font-medium text-gray-500 uppercase tracking-wider">Actions</th>
                  </tr>
                </thead>
                <tbody className="bg-white divide-y divide-gray-200">
                  {filteredCases.map((c) => (
                    <tr key={c.id} className="hover:bg-gray-50 transition">
                      <td className="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-900">#{c.id}</td>
                      <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900 font-medium">{c.title}</td>
                      <td className="px-6 py-4 whitespace-nowrap">
                        <span className={`px-3 py-1 rounded-full text-xs font-semibold ${statusStyles[c.status]}`}>
                          {c.status}
                        </span>
                      </td>
                      <td className="px-6 py-4 whitespace-nowrap">
                        <span className={`px-3 py-1 rounded-full text-xs font-semibold ${priorityStyles[c.priority]}`}>
                          {c.priority}
                        </span>
                      </td>
                      <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">{c.assignedTo}</td>
                      <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">{c.createdAt}</td>
                      <td className="px-6 py-4 whitespace-nowrap text-right text-sm font-medium">
                        <div className="flex justify-end gap-2">
                          <button className="text-blue-600 hover:text-blue-900 p-1 rounded hover:bg-blue-50 transition">
                            <FiEye className="h-5 w-5" />
                          </button>
                          <button className="text-gray-600 hover:text-gray-900 p-1 rounded hover:bg-gray-50 transition">
                            <FiEdit2 className="h-5 w-5" />
                          </button>
                        </div>
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          ) : (
            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4 p-4">
              {filteredCases.map((c) => (
                <div key={c.id} className="border border-gray-200 rounded-lg overflow-hidden hover:shadow-md transition">
                  <div className="p-4">
                    <div className="flex justify-between items-start mb-2">
                      <h3 className="text-lg font-semibold text-gray-800">{c.title}</h3>
                      <span className={`px-2 py-1 rounded-full text-xs font-semibold ${statusStyles[c.status]}`}>
                        {c.status}
                      </span>
                    </div>
                    <p className="text-sm text-gray-600 mb-3 line-clamp-2">{c.description}</p>
                    <div className="flex justify-between items-center mb-3">
                      <span className={`px-2 py-1 rounded-full text-xs font-semibold ${priorityStyles[c.priority]}`}>
                        {c.priority} Priority
                      </span>
                      <span className="text-xs text-gray-500">Created: {c.createdAt}</span>
                    </div>
                    <div className="flex items-center justify-between pt-3 border-t border-gray-100">
                      <span className="text-sm font-medium text-gray-700">{c.assignedTo}</span>
                      <div className="flex gap-2">
                        <button className="text-blue-600 hover:text-blue-900 p-1 rounded hover:bg-blue-50 transition">
                          <FiEye className="h-5 w-5" />
                        </button>
                        <button className="text-gray-600 hover:text-gray-900 p-1 rounded hover:bg-gray-50 transition">
                          <FiEdit2 className="h-5 w-5" />
                        </button>
                      </div>
                    </div>
                  </div>
                </div>
              ))}
            </div>
          )}
        </div>

        {filteredCases.length === 0 && (
          <div className="text-center py-12">
            <h3 className="text-lg font-medium text-gray-900">No cases found</h3>
            <p className="mt-1 text-sm text-gray-500">Try adjusting your search or filter criteria</p>
          </div>
        )}

        <div className="flex items-center justify-between mt-4 text-sm text-gray-600">
          <div>Showing {filteredCases.length} of {cases.length} cases</div>
          <div className="flex gap-2">
            <button className="px-3 py-1 border rounded-md bg-white hover:bg-gray-50 disabled:opacity-50" disabled>
              Previous
            </button>
            <button className="px-3 py-1 border rounded-md bg-white hover:bg-gray-50">
              Next
            </button>
          </div>
        </div>
      </div>
    </main>
  );
}