'use client';

import { useParams } from 'next/navigation';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { Button } from '@/components/ui/button';
import { PlusIcon, SearchIcon, FilterIcon, PhoneIcon, MailIcon, FileTextIcon } from 'lucide-react';
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

export default function WitnessesPage() {
  const { role } = useParams();

  // Sample witness data
  const witnesses = [
    {
      id: 'WIT-001',
      name: 'Mark Lee',
      testimony: 'Saw suspect running away',
      contact: '0987654321',
      email: 'mark.lee@example.com',
      status: 'Interview Pending',
      reliability: 'High',
      lastContact: '2023-06-15',
      caseId: 'CASE-1024'
    },
    {
      id: 'WIT-002',
      name: 'Sarah Connor',
      testimony: 'Heard loud argument before incident',
      contact: '0555123456',
      email: 'sarah.c@example.com',
      status: 'Statement Taken',
      reliability: 'Medium',
      lastContact: '2023-06-12',
      caseId: 'CASE-1025'
    },
    {
      id: 'WIT-003',
      name: 'James Wilson',
      testimony: 'Security camera footage available',
      contact: '0777888999',
      email: 'james.w@example.com',
      status: 'Court Summoned',
      reliability: 'High',
      lastContact: '2023-06-08',
      caseId: 'CASE-1024'
    }
  ];

  const statusVariant = (status: string) => {
    switch (status) {
      case 'Interview Pending': return 'warning';
      case 'Statement Taken': return 'secondary';
      case 'Court Summoned': return 'success';
      default: return 'default';
    }
  };

  const reliabilityVariant = (reliability: string) => {
    switch (reliability) {
      case 'High': return 'success';
      case 'Medium': return 'warning';
      case 'Low': return 'destructive';
      default: return 'default';
    }
  };

  return (
    <div className="p-6 space-y-6 max-w-8xl mx-auto">
      {/* Breadcrumb */}
      <nav className="flex items-center space-x-2 text-sm">
        <Link href="/" className="text-gray-500 hover:text-primary hover:underline">
          Home
        </Link>
        <span className="text-gray-400">/</span>
        <Link href={`/${role}`} className="text-gray-500 hover:text-primary hover:underline capitalize">
          {role}
        </Link>
        <span className="text-gray-400">/</span>
        <span className="text-primary font-medium">Witnesses</span>
      </nav>

      {/* Page Header */}
      <div className="flex flex-col md:flex-row justify-between items-start md:items-center gap-4">
        <div>
          <h1 className="text-2xl font-bold text-gray-800">Witness Management</h1>
          <p className="text-sm text-gray-500 mt-1">
            {role === 'investigator' 
              ? 'Witnesses in your cases' 
              : 'All witness records'}
          </p>
        </div>
        <div className="flex gap-3">
          <Button className="rounded-lg gap-2 border-gray-300 " variant="outline">
            <PlusIcon className="h-4 w-4" />
            Add Witness
          </Button>
          <Button variant="outline" className="rounded-lg gap-2 border-gray-300">
            <FilterIcon className="h-4 w-4" />
            Filter
          </Button>
        </div>
      </div>

      {/* Stats Cards */}
      <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
        <Card className="rounded-xl border border-gray-200">
          <CardHeader className="pb-2 px-5 pt-5">
            <CardTitle className="text-sm font-medium text-gray-600">Total Witnesses</CardTitle>
          </CardHeader>
          <CardContent className="px-5 pb-5">
            <div className="text-2xl font-bold text-gray-800">{witnesses.length}</div>
          </CardContent>
        </Card>
        <Card className="rounded-xl border border-gray-200">
          <CardHeader className="pb-2 px-5 pt-5">
            <CardTitle className="text-sm font-medium text-gray-600">Pending Interviews</CardTitle>
          </CardHeader>
          <CardContent className="px-5 pb-5">
            <div className="text-2xl font-bold text-gray-800">
              {witnesses.filter(w => w.status === 'Interview Pending').length}
            </div>
          </CardContent>
        </Card>
        <Card className="rounded-xl border border-gray-200">
          <CardHeader className="pb-2 px-5 pt-5">
            <CardTitle className="text-sm font-medium text-gray-600">High Reliability</CardTitle>
          </CardHeader>
          <CardContent className="px-5 pb-5">
            <div className="text-2xl font-bold text-gray-800">
              {witnesses.filter(w => w.reliability === 'High').length}
            </div>
          </CardContent>
        </Card>
      </div>

      {/* Witnesses Table */}
      <Card className="rounded-xl border border-gray-200 overflow-hidden">
        <CardHeader className="border-b p-5 bg-gray-50">
          <div className="flex flex-col md:flex-row justify-between items-start md:items-center gap-4">
            <CardTitle className="text-lg font-semibold text-gray-800">Witness Records</CardTitle>
            <div className="relative w-full md:w-auto">
              <SearchIcon className="absolute left-3 top-1/2 -translate-y-1/2 h-4 w-4 text-gray-400" />
              <Input
                placeholder="Search witnesses..."
                className="pl-9 w-full md:w-[300px] rounded-lg"
              />
            </div>
          </div>
        </CardHeader>
        <CardContent className="p-0">
          <Table>
            <TableHeader>
              <TableRow>
                <TableHead className="w-[120px] px-5 py-3">Witness ID</TableHead>
                <TableHead className="px-5 py-3">Name</TableHead>
                <TableHead className="px-5 py-3">Testimony</TableHead>
                <TableHead className="px-5 py-3">Contact</TableHead>
                <TableHead className="px-5 py-3">Case ID</TableHead>
                <TableHead className="px-5 py-3">Reliability</TableHead>
                <TableHead className="px-5 py-3">Status</TableHead>
                <TableHead className="text-right px-5 py-3">Actions</TableHead>
              </TableRow>
            </TableHeader>
            <TableBody>
              {witnesses.map((witness) => (
                <TableRow key={witness.id} className="hover:bg-gray-50/50">
                  <TableCell className="px-5 py-4 font-medium">{witness.id}</TableCell>
                  <TableCell className="px-5 py-4 font-medium">{witness.name}</TableCell>
                  <TableCell className="px-5 py-4">
                    <div className="flex items-center gap-2">
                      <FileTextIcon className="h-4 w-4 text-gray-500 flex-shrink-0" />
                      <span className="line-clamp-1">{witness.testimony}</span>
                    </div>
                  </TableCell>
                  <TableCell className="px-5 py-4">
                    <div className="flex flex-col gap-1">
                      <div className="flex items-center gap-2">
                        <PhoneIcon className="h-4 w-4 text-gray-500" />
                        <span>{witness.contact}</span>
                      </div>
                      <div className="flex items-center gap-2">
                        <MailIcon className="h-4 w-4 text-gray-500" />
                        <span className="text-sm text-gray-600 truncate max-w-[160px]">
                          {witness.email}
                        </span>
                      </div>
                    </div>
                  </TableCell>
                  <TableCell className="px-5 py-4">
                    <Badge variant="outline" className="rounded-md">
                      {witness.caseId}
                    </Badge>
                  </TableCell>
                  <TableCell className="px-5 py-4">
                    <Badge variant={reliabilityVariant(witness.reliability)} className="rounded-md">
                      {witness.reliability}
                    </Badge>
                  </TableCell>
                  <TableCell className="px-5 py-4">
                    <Badge variant={statusVariant(witness.status)} className="rounded-md">
                      {witness.status}
                    </Badge>
                  </TableCell>
                  <TableCell className="px-5 py-4 text-right space-x-2">
                    <Button size="sm" variant="outline" className="rounded-lg border-gray-300">
                      View
                    </Button>
                    {(role === 'investigator' || role === 'prosecutor') && (
                      <Button size="sm" className="rounded-lg border-gray-300" variant="outline">
                        Interview
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