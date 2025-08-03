'use client';

import { useParams } from 'next/navigation';
import Link from 'next/link';
import { Button } from '@/components/ui/button';
import { PlusIcon, DownloadIcon, FilterIcon } from 'lucide-react';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from '@/components/ui/table';
import Badge from '@/components/ui/badge';

export default function ReportsPage() {
  const { role } = useParams();

  // Sample report data
  const reports = [
    {
      id: 'RPT-001',
      reporter: 'John Doe',
      type: 'Assault',
      date: '2025-08-01',
      status: 'Pending',
      priority: 'High'
    },
    {
      id: 'RPT-002',
      reporter: 'Jane Smith',
      type: 'Burglary',
      date: '2025-08-02',
      status: 'Under Review',
      priority: 'Medium'
    },
    {
      id: 'RPT-003',
      reporter: 'Robert Johnson',
      type: 'Fraud',
      date: '2025-08-03',
      status: 'Completed',
      priority: 'Low'
    }
  ];

  const statusVariant = (status: string) => {
    switch (status) {
      case 'Pending': return 'warning';
      case 'Under Review': return 'secondary';
      case 'Completed': return 'success';
      default: return 'default';
    }
  };

  const priorityVariant = (priority: string) => {
    switch (priority) {
      case 'High': return 'destructive';
      case 'Medium': return 'warning';
      case 'Low': return 'success';
      default: return 'default';
    }
  };

  return (
    <div className="p-6 max-w-7xl mx-auto space-y-6">
      {/* Breadcrumb */}
      <nav className="flex items-center space-x-2 text-sm text-gray-600">
        <Link href="/" className="hover:text-primary hover:underline">Home</Link>
        <span>/</span>
        <Link href={`/${role}`} className="hover:text-primary hover:underline capitalize">
          {role}
        </Link>
        <span>/</span>
        <span className="text-primary font-medium">Reports</span>
      </nav>

      {/* Page Header */}
      <div className="flex flex-col md:flex-row justify-between items-start md:items-center gap-4">
        <div>
          <h1 className="text-2xl font-bold text-gray-800">Case Reports</h1>
          <p className="text-sm text-gray-500 mt-1">
            {role === 'investigator' 
              ? 'Reports assigned to you' 
              : 'All case reports'}
          </p>
        </div>
        <div className="flex gap-2">
          <Button className="rounded-lg gap-2">
            <PlusIcon className="h-4 w-4" />
            New Report
          </Button>
          <Button variant="outline" className="rounded-lg gap-2">
            <DownloadIcon className="h-4 w-4" />
            Export
          </Button>
        </div>
      </div>

      {/* Stats Cards */}
      <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
        <Card className="rounded-xl border border-gray-200">
          <CardHeader className="pb-2 px-5 pt-5">
            <CardTitle className="text-sm font-medium text-gray-600">Total Reports</CardTitle>
          </CardHeader>
          <CardContent className="px-5 pb-5">
            <div className="text-2xl font-bold text-gray-800">{reports.length}</div>
          </CardContent>
        </Card>
        <Card className="rounded-xl border border-gray-200">
          <CardHeader className="pb-2 px-5 pt-5">
            <CardTitle className="text-sm font-medium text-gray-600">Pending Review</CardTitle>
          </CardHeader>
          <CardContent className="px-5 pb-5">
            <div className="text-2xl font-bold text-gray-800">
              {reports.filter(r => r.status === 'Pending').length}
            </div>
          </CardContent>
        </Card>
        <Card className="rounded-xl border border-gray-200">
          <CardHeader className="pb-2 px-5 pt-5">
            <CardTitle className="text-sm font-medium text-gray-600">High Priority</CardTitle>
          </CardHeader>
          <CardContent className="px-5 pb-5">
            <div className="text-2xl font-bold text-gray-800">
              {reports.filter(r => r.priority === 'High').length}
            </div>
          </CardContent>
        </Card>
      </div>

      {/* Reports Table */}
      <Card className="rounded-xl border border-gray-200 overflow-hidden">
        <CardHeader className="border-b p-5 bg-gray-50">
          <div className="flex flex-col md:flex-row justify-between items-start md:items-center gap-4">
            <CardTitle className="text-lg font-semibold text-gray-800">Report Records</CardTitle>
            <div className="flex items-center gap-2">
              <Button variant="outline" size="sm" className="rounded-lg gap-2">
                <FilterIcon className="h-4 w-4" />
                Filter
              </Button>
            </div>
          </div>
        </CardHeader>
        <CardContent className="p-0">
          <Table>
            <TableHeader>
              <TableRow>
                <TableHead className="w-[120px] px-5 py-3">Report ID</TableHead>
                <TableHead className="px-5 py-3">Reporter</TableHead>
                <TableHead className="px-5 py-3">Type</TableHead>
                <TableHead className="px-5 py-3">Date</TableHead>
                <TableHead className="px-5 py-3">Priority</TableHead>
                <TableHead className="px-5 py-3">Status</TableHead>
                <TableHead className="text-right px-5 py-3">Actions</TableHead>
              </TableRow>
            </TableHeader>
            <TableBody>
              {reports.map((report) => (
                <TableRow key={report.id} className="hover:bg-gray-50/50">
                  <TableCell className="px-5 py-4 font-medium">{report.id}</TableCell>
                  <TableCell className="px-5 py-4">{report.reporter}</TableCell>
                  <TableCell className="px-5 py-4">{report.type}</TableCell>
                  <TableCell className="px-5 py-4">{report.date}</TableCell>
                  <TableCell className="px-5 py-4">
                    <Badge variant={priorityVariant(report.priority)} className="rounded-md">
                      {report.priority}
                    </Badge>
                  </TableCell>
                  <TableCell className="px-5 py-4">
                    <Badge variant={statusVariant(report.status)} className="rounded-md">
                      {report.status}
                    </Badge>
                  </TableCell>
                  <TableCell className="px-5 py-4 text-right space-x-2">
                    <Button size="sm" variant="outline" className="rounded-lg">
                      View
                    </Button>
                    {role !== 'admin' && (
                      <Button size="sm" className="rounded-lg">
                        Edit
                      </Button>
                    )}
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </CardContent>
      </Card>
    </div>
  );
}