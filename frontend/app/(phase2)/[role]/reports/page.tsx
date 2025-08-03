"use client"

import { useState } from "react"
import { useRouter, useParams } from "next/navigation"
import { Search, LogOut, ChevronDown } from "lucide-react"
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table"
import { Input } from "@/components/ui/input"
import { Button } from "@/components/ui/button"

// Mock data for the investigation reports
const mockReports = [
  {
    caseId: "#20462",
    typeOfCrime: "Violent Crimes",
    levelOfSeverity: "3rd Degree Felony",
    date: "13/05/2022",
    receivingUnit: "Local PD - Investigation Division",
    location: "Austin, TX",
    status: "Under Investigation",
    statusColor: "text-green-600 bg-green-50"
  },
  {
    caseId: "#20462",
    typeOfCrime: "Violent Crimes", 
    levelOfSeverity: "3rd Degree Felony",
    date: "13/05/2022",
    receivingUnit: "Violent Crimes Unit (PD)",
    location: "Chicago, IL",
    status: "Under Investigation",
    statusColor: "text-green-600 bg-green-50"
  },
  {
    caseId: "#18933",
    typeOfCrime: "Property Crimes",
    levelOfSeverity: "Misdemeanor",
    date: "22/05/2022",
    receivingUnit: "Local PD - Investigation Division",
    location: "New York, NY",
    status: "Under Investigation", 
    statusColor: "text-green-600 bg-green-50"
  },
]

export default function ReportsPage() {
  const router = useRouter()
  const params = useParams()
  const [searchTerm, setSearchTerm] = useState("")
  const [entriesPerPage, setEntriesPerPage] = useState(10)
  const [currentPage, setCurrentPage] = useState(1)

  const handleRowClick = (caseId: string) => {
    // Remove the # symbol from caseId for URL
    const reportId = caseId.replace('#', '')
    // Get current role from URL params
    const role = params.role
    // Navigate directly to initial-response as the default starting point
    router.push(`/${role}/reports/${reportId}/initial-response`)
  }

  // Filter reports based on search term
  const filteredReports = mockReports.filter(report =>
    Object.values(report).some(value =>
      value.toString().toLowerCase().includes(searchTerm.toLowerCase())
    )
  )

  // Calculate pagination
  const totalPages = Math.ceil(filteredReports.length / entriesPerPage)
  const startIndex = (currentPage - 1) * entriesPerPage
  const paginatedReports = filteredReports.slice(startIndex, startIndex + entriesPerPage)

  return (
    <div className="min-h-screen bg-gradient-to-br from-indigo-50 via-blue-50 to-purple-50">
      {/* Header */}
      <div className="bg-white border-b border-gray-200 shadow-sm">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="flex justify-between items-center h-16">
            <div className="flex items-center space-x-4">
              <div className="text-xl font-semibold text-gray-900">
                Law Enforcement Portal
              </div>
            </div>
            
            <div className="flex items-center space-x-4">
              <div className="text-right">
                <div className="text-sm font-medium text-gray-900">MATTHA, JOHN</div>
                <div className="text-xs text-gray-500">Sheriff</div>
              </div>
              <Button variant="ghost" size="icon" className="text-gray-500 hover:text-gray-700">
                <LogOut className="h-5 w-5" />
              </Button>
            </div>
          </div>
        </div>
      </div>

      {/* Main Content */}
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
        {/* Page Title */}
        <div className="mb-8">
          <h1 className="text-3xl font-bold text-gray-900 mb-2">Preliminary Investigation</h1>
          <p className="text-gray-600">Manage and track ongoing criminal investigations</p>
        </div>

        {/* Controls */}
        <div className="bg-white rounded-lg shadow-sm border border-gray-200 p-6 mb-6">
          <div className="flex flex-col sm:flex-row justify-between items-start sm:items-center gap-4">
            <div className="flex items-center gap-2">
              <span className="text-sm text-gray-700">Show</span>
              <div className="relative">
                <select 
                  value={entriesPerPage}
                  onChange={(e) => setEntriesPerPage(Number(e.target.value))}
                  className="appearance-none bg-white border border-gray-300 rounded-md px-3 py-2 pr-8 text-sm focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-blue-500"
                >
                  <option value={5}>5</option>
                  <option value={10}>10</option>
                  <option value={25}>25</option>
                  <option value={50}>50</option>
                </select>
                <ChevronDown className="absolute right-2 top-1/2 transform -translate-y-1/2 h-4 w-4 text-gray-400 pointer-events-none" />
              </div>
              <span className="text-sm text-gray-700">entries</span>
            </div>
            
            <div className="relative">
              <Search className="absolute left-3 top-1/2 transform -translate-y-1/2 h-4 w-4 text-gray-400" />
              <Input
                type="text"
                placeholder="Search..."
                value={searchTerm}
                onChange={(e) => setSearchTerm(e.target.value)}
                className="pl-10 w-80"
              />
            </div>
          </div>
        </div>

        {/* Table */}
        <div className="bg-white rounded-lg shadow-sm border border-gray-200 overflow-hidden">
          <Table>
            <TableHeader>
              <TableRow className="bg-gray-50">
                <TableHead className="font-semibold text-gray-900">Case ID</TableHead>
                <TableHead className="font-semibold text-gray-900">Type of Crime</TableHead>
                <TableHead className="font-semibold text-gray-900">Level of severity</TableHead>
                <TableHead className="font-semibold text-gray-900">Date</TableHead>
                <TableHead className="font-semibold text-gray-900">Receiving Unit</TableHead>
                <TableHead className="font-semibold text-gray-900">Location</TableHead>
                <TableHead className="font-semibold text-gray-900">Status</TableHead>
              </TableRow>
            </TableHeader>
            <TableBody>
              {paginatedReports.map((report, index) => (
                <TableRow 
                  key={index} 
                  className="hover:bg-gray-50 cursor-pointer transition-colors"
                  onClick={() => handleRowClick(report.caseId)}
                >
                  <TableCell className="font-medium text-blue-600 hover:text-blue-800">
                    {report.caseId}
                  </TableCell>
                  <TableCell className="text-gray-900">
                    {report.typeOfCrime}
                  </TableCell>
                  <TableCell className="text-gray-700">
                    {report.levelOfSeverity}
                  </TableCell>
                  <TableCell className="text-gray-700">
                    {report.date}
                  </TableCell>
                  <TableCell className="text-gray-700">
                    {report.receivingUnit}
                  </TableCell>
                  <TableCell className="text-gray-700">
                    {report.location}
                  </TableCell>
                  <TableCell>
                    <span className={`inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium ${report.statusColor}`}>
                      {report.status}
                    </span>
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </div>

        {/* Pagination */}
        <div className="bg-white border-t border-gray-200 px-6 py-4 rounded-b-lg">
          <div className="flex items-center justify-center">
            <div className="flex items-center space-x-2">
              <Button
                variant="outline"
                size="sm"
                onClick={() => setCurrentPage(prev => Math.max(prev - 1, 1))}
                disabled={currentPage === 1}
                className="text-gray-600"
              >
                Previous
              </Button>
              
              {[...Array(totalPages)].map((_, index) => {
                const pageNumber = index + 1
                return (
                  <Button
                    key={pageNumber}
                    variant={currentPage === pageNumber ? "default" : "outline"}
                    size="sm"
                    onClick={() => setCurrentPage(pageNumber)}
                    className={currentPage === pageNumber ? "bg-blue-600 text-white" : "text-gray-600"}
                  >
                    {pageNumber}
                  </Button>
                )
              })}
              
              <Button
                variant="outline" 
                size="sm"
                onClick={() => setCurrentPage(prev => Math.min(prev + 1, totalPages))}
                disabled={currentPage === totalPages}
                className="text-gray-600"
              >
                Next
              </Button>
            </div>
          </div>
        </div>
      </div>
    </div>
  )
}
