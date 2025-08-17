'use client';

import { useParams } from 'next/navigation';
import { reports as rawReports } from '@/data/mockData';
import { FiArrowLeft } from 'react-icons/fi';
import { useRouter } from 'next/navigation';
import Badge from '@/components/ui/badge';

export default function ReportDetailPage() {
    const router = useRouter();
    const params = useParams();
    const reportId = params.id as string;
    
    const report = rawReports.find(r => r.id === reportId);
    
    if (!report) {
        return (
            <div className="p-8 text-center">
                <h1 className="text-2xl font-bold">Report not found</h1>
                <button 
                    onClick={() => router.back()}
                    className="mt-4 text-blue-600 hover:underline"
                >
                    Go back
                </button>
            </div>
        );
    }
    
    return (
        <div className="p-4 md:p-8 max-w-4xl mx-auto">
            <button 
                onClick={() => router.back()}
                className="flex items-center gap-2 text-gray-600 hover:text-gray-900 mb-6"
            >
                <FiArrowLeft /> Back to Reports
            </button>
            
            <div className="bg-white rounded-lg shadow-sm border border-gray-200 overflow-hidden">
                <div className="p-6 border-b border-gray-200">
                    <div className="flex justify-between items-start">
                        <div>
                            <h1 className="text-2xl font-bold text-gray-800">Report #{report.id}</h1>
                            <p className="text-gray-600">{report.type}</p>
                        </div>
                        <Badge status={report.status} />
                    </div>
                </div>
                
                <div className="p-6 grid grid-cols-1 md:grid-cols-2 gap-6">
                    <div>
                        <h2 className="text-lg font-semibold mb-4">Report Details</h2>
                        <div className="space-y-4">
                            <div>
                                <p className="text-sm text-gray-500">Date Reported</p>
                                <p>{new Date(report.date).toLocaleDateString()}</p>
                            </div>
                            <div>
                                <p className="text-sm text-gray-500">Severity</p>
                                <p>{report.severity}</p>
                            </div>
                            <div>
                                <p className="text-sm text-gray-500">Reporter</p>
                                <p>{report.reporter}</p>
                            </div>
                        </div>
                    </div>
                    
                    <div>
                        <h2 className="text-lg font-semibold mb-4">Description</h2>
                        <div className="bg-gray-50 p-4 rounded">
                            <p className="text-gray-700">{report.description || 'No description provided'}</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}