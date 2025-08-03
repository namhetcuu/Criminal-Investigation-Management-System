'use client';

import { useParams } from 'next/navigation';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { Button } from '@/components/ui/button';
import { PlusIcon, SearchIcon, FilterIcon, DownloadIcon } from 'lucide-react';
import { Input } from '@/components/ui/input';
import Badge from '@/components/ui/badge';
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from '@/components/ui/table';
import Link from 'next/link';

export default function EvidencePage() {
  const { role } = useParams();

  // Mock data
  const evidenceData = [
    {
      id: 'E001',
      description: 'Knife with fingerprints',
      type: 'Physical',
      status: 'Analyzed',
      collectedDate: '2023-06-15',
      collectedBy: 'Officer Smith',
      caseId: 'C-1024',
    },
    {
      id: 'E002',
      description: 'CCTV footage - Parking lot',
      type: 'Digital',
      status: 'Pending',
      collectedDate: '2023-06-18',
      collectedBy: 'Detective Johnson',
      caseId: 'C-1025',
    }
  ];

  const statusVariant = (status: string) => {
    switch (status) {
      case 'Analyzed': return 'success';
      case 'Pending': return 'warning';
      case 'In Lab': return 'secondary';
      default: return 'default';
    }
  };

  const typeVariant = (type: string) => {
    switch (type) {
      case 'Physical': return 'default';
      case 'Digital': return 'outline';
      case 'Biological': return 'destructive';
      default: return 'default';
    }
  };

  return (
    <div className="p-6 space-y-6 max-w-8xl mx-auto">
      <nav className="flex items-center space-x-2 text-sm">
        <Link href="/" className="text-gray-500 hover:text-primary hover:underline">
          Home
        </Link>
        <span className="text-gray-400">/</span>
        <Link href={`/${role}`} className="text-gray-500 hover:text-primary hover:underline capitalize">
          {role}
        </Link>
        <span className="text-gray-400">/</span>
        <span className="text-primary font-medium">Evidence</span>
      </nav>

      {/* Header Section */}
      <div className="flex flex-col md:flex-row justify-between items-start md:items-center gap-4">
        <div>
          <h1 className="text-2xl font-bold text-gray-800">Evidence Management</h1>
          <p className="text-sm text-gray-500 mt-1">
            {role === 'investigator' 
              ? 'Your collected evidence' 
              : 'All evidence records'}
          </p>
        </div>
        
        <div className="flex flex-col sm:flex-row gap-3 w-full md:w-auto">
          <div className="relative">
            <SearchIcon className="absolute left-3 top-1/2 -translate-y-1/2 h-4 w-4 text-gray-400" />
            <Input
              placeholder="Search evidence..."
              className="pl-9 w-full md:w-[300px] rounded-lg"
            />
          </div>
          <Button className="gap-2 rounded-lg border-gray-300" size="sm" 
                      variant="outline">
            <PlusIcon className="h-4 w-4" />
            Add Evidence
          </Button>
        </div>
      </div>

      {/* Stats Cards */}
      <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
        {[
          { title: 'Total Evidence', value: evidenceData.length },
          { 
            title: 'Pending Analysis', 
            value: evidenceData.filter(e => e.status === 'Pending').length 
          },
          { 
            title: 'Physical Evidence', 
            value: evidenceData.filter(e => e.type === 'Physical').length 
          }
        ].map((stat, index) => (
          <Card key={index} className="rounded-xl border border-gray-200">
            <CardHeader className="pb-2 px-5 pt-5">
              <CardTitle className="text-sm font-medium text-gray-600">
                {stat.title}
              </CardTitle>
            </CardHeader>
            <CardContent className="px-5 pb-5">
              <div className="text-2xl font-bold text-gray-800">
                {stat.value}
              </div>
            </CardContent>
          </Card>
        ))}
      </div>

      {/* Evidence Table Section */}
      <Card className="rounded-xl border border-gray-200 overflow-hidden">
        <CardHeader className="border-b p-5 bg-gray-50">
          <div className="flex flex-col md:flex-row justify-between items-start md:items-center gap-4">
            <CardTitle className="text-lg font-semibold text-gray-800">
              Evidence Records
            </CardTitle>
            <div className="flex items-center gap-2">
              <Button 
                variant="outline" 
                size="sm" 
                className="rounded-lg gap-2 border-gray-300"
              >
                <FilterIcon className="h-4 w-4" />
                Filter
              </Button>
              <Button 
                variant="outline" 
                size="sm" 
                className="rounded-lg gap-2 border-gray-300"
              >
                <DownloadIcon className="h-4 w-4" />
                Export
              </Button>
            </div>
          </div>
        </CardHeader>
        <CardContent className="p-0">
          <Table>
            <TableHeader>
              <TableRow className="hover:bg-transparent">
                {[
                  'Evidence ID',
                  'Description',
                  'Type',
                  'Status',
                  'Case ID',
                  'Collected By',
                  'Date',
                  'Actions'
                ].map((header, index) => (
                  <TableHead 
                    key={index} 
                    className={`
                      px-5 py-3 text-gray-700 font-medium
                      ${index === 0 ? 'w-[100px]' : ''}
                      ${index === 7 ? 'text-right' : ''}
                    `}
                  >
                    {header}
                  </TableHead>
                ))}
              </TableRow>
            </TableHeader>
            <TableBody>
              {evidenceData.map((evidence) => (
                <TableRow 
                  key={evidence.id} 
                  className="hover:bg-gray-50/50 border-t border-gray-100"
                >
                  <TableCell className="px-5 py-4 font-medium text-gray-800">
                    {evidence.id}
                  </TableCell>
                  <TableCell className="px-5 py-4 max-w-[200px] truncate">
                    {evidence.description}
                  </TableCell>
                  <TableCell className="px-5 py-4">
                    <Badge 
                      variant={typeVariant(evidence.type)} 
                      className="rounded-md"
                    >
                      {evidence.type}
                    </Badge>
                  </TableCell>
                  <TableCell className="px-5 py-4">
                    <Badge 
                      variant={statusVariant(evidence.status)} 
                      className="rounded-md"
                    >
                      {evidence.status}
                    </Badge>
                  </TableCell>
                  <TableCell className="px-5 py-4">
                    <Badge variant="outline" className="rounded-md">
                      {evidence.caseId}
                    </Badge>
                  </TableCell>
                  <TableCell className="px-5 py-4 text-gray-600">
                    {evidence.collectedBy}
                  </TableCell>
                  <TableCell className="px-5 py-4 text-gray-600">
                    {evidence.collectedDate}
                  </TableCell>
                  <TableCell className="px-5 py-4 text-right space-x-2">
                    <Button 
                      size="sm" 
                      variant="outline" 
                      className="rounded-lg border-gray-300"
                    >
                      View
                    </Button>
                    {role !== 'admin' && (
                      <Button size="sm" className="rounded-lg border-gray-300" variant="outline">
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