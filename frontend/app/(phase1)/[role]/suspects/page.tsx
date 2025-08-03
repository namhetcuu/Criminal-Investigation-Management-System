'use client';

import { useParams } from 'next/navigation';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { Button } from '@/components/ui/button';
import { PlusIcon, SearchIcon, FilterIcon } from 'lucide-react';
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

export default function SuspectsPage() {
  const { role } = useParams();

  // Sample suspect data
  const suspects = [
    {
      id: 'S-001',
      name: 'Tom Hardy',
      crime: 'Burglary',
      status: 'Under Surveillance',
      priority: 'High',
      lastSeen: '2023-06-15'
    },
    {
      id: 'S-002',
      name: 'Jane Doe',
      crime: 'Fraud',
      status: 'Wanted',
      priority: 'Medium',
      lastSeen: '2023-06-10'
    },
    {
      id: 'S-003',
      name: 'John Smith',
      crime: 'Assault',
      status: 'In Custody',
      priority: 'Low',
      lastSeen: '2023-05-28'
    }
  ];

  const statusVariant = (status: string) => {
    switch (status) {
      case 'Under Surveillance': return 'warning';
      case 'Wanted': return 'destructive';
      case 'In Custody': return 'success';
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
        <span className="text-primary font-medium">Suspects</span>
      </nav>

      {/* Page Header */}
      <div className="flex flex-col md:flex-row justify-between items-start md:items-center gap-4">
        <div>
          <h1 className="text-2xl font-bold text-gray-800">Suspect Management</h1>
          <p className="text-sm text-gray-500 mt-1">
            {role === 'investigator' 
              ? 'Your assigned suspects' 
              : 'All suspect records'}
          </p>
        </div>
        <div className="flex gap-3">
          <Button className="rounded-lg gap-2 border-gray-300" variant="outline">
            <PlusIcon className="h-4 w-4" />
            Add Suspect
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
            <CardTitle className="text-sm font-medium text-gray-600">Total Suspects</CardTitle>
          </CardHeader>
          <CardContent className="px-5 pb-5">
            <div className="text-2xl font-bold text-gray-800">{suspects.length}</div>
          </CardContent>
        </Card>
        <Card className="rounded-xl border border-gray-200">
          <CardHeader className="pb-2 px-5 pt-5">
            <CardTitle className="text-sm font-medium text-gray-600">Wanted</CardTitle>
          </CardHeader>
          <CardContent className="px-5 pb-5">
            <div className="text-2xl font-bold text-gray-800">
              {suspects.filter(s => s.status === 'Wanted').length}
            </div>
          </CardContent>
        </Card>
        <Card className="rounded-xl border border-gray-200">
          <CardHeader className="pb-2 px-5 pt-5">
            <CardTitle className="text-sm font-medium text-gray-600">High Priority</CardTitle>
          </CardHeader>
          <CardContent className="px-5 pb-5">
            <div className="text-2xl font-bold text-gray-800">
              {suspects.filter(s => s.priority === 'High').length}
            </div>
          </CardContent>
        </Card>
      </div>

      {/* Suspects Table */}
      <Card className="rounded-xl border border-gray-200 overflow-hidden">
        <CardHeader className="border-b p-5 bg-gray-50">
          <div className="flex flex-col md:flex-row justify-between items-start md:items-center gap-4">
            <CardTitle className="text-lg font-semibold text-gray-800">Suspect Records</CardTitle>
            <div className="relative w-full md:w-auto">
              <SearchIcon className="absolute left-3 top-1/2 -translate-y-1/2 h-4 w-4 text-gray-400" />
              <Input
                placeholder="Search suspects..."
                className="pl-9 w-full md:w-[300px] rounded-lg"
              />
            </div>
          </div>
        </CardHeader>
        <CardContent className="p-0">
          <Table>
            <TableHeader>
              <TableRow>
                <TableHead className="w-[120px] px-5 py-3">Suspect ID</TableHead>
                <TableHead className="px-5 py-3">Name</TableHead>
                <TableHead className="px-5 py-3">Crime</TableHead>
                <TableHead className="px-5 py-3">Last Seen</TableHead>
                <TableHead className="px-5 py-3">Priority</TableHead>
                <TableHead className="px-5 py-3">Status</TableHead>
                <TableHead className="text-right px-5 py-3">Actions</TableHead>
              </TableRow>
            </TableHeader>
            <TableBody>
              {suspects.map((suspect) => (
                <TableRow key={suspect.id} className="hover:bg-gray-50/50">
                  <TableCell className="px-5 py-4 font-medium">{suspect.id}</TableCell>
                  <TableCell className="px-5 py-4 font-medium">{suspect.name}</TableCell>
                  <TableCell className="px-5 py-4">{suspect.crime}</TableCell>
                  <TableCell className="px-5 py-4 text-gray-600">{suspect.lastSeen}</TableCell>
                  <TableCell className="px-5 py-4">
                    <Badge variant={priorityVariant(suspect.priority)} className="rounded-md">
                      {suspect.priority}
                    </Badge>
                  </TableCell>
                  <TableCell className="px-5 py-4">
                    <Badge variant={statusVariant(suspect.status)} className="rounded-md">
                      {suspect.status}
                    </Badge>
                  </TableCell>
                  <TableCell className="px-5 py-4 text-right space-x-2">
                    <Button size="sm" variant="outline" className="rounded-lg border-gray-300">
                      View
                    </Button>
                    {(role === 'investigator' || role === 'supervisor') && (
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