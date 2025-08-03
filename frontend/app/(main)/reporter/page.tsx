'use client';

import Link from 'next/link';
import MultiStepForm from '@/components/form/MultiStepForm';

export default function ReporterPage() {
    return (
        <div className="px-4 sm:px-6 lg:px-8 py-4 max-w-4xl mx-auto">
            {/* Breadcrumb */}
            <div className="text-sm text-gray-600 mb-4">
                <nav className="flex items-center space-x-2">
                    <Link href="/" className="hover:underline text-gray-500 hover:text-black">
                        Home
                    </Link>
                    <span>&gt;</span>
                    <span className="text-black font-medium">Report</span>
                </nav>
            </div>

            {/* Centered Title */}
            <h1 className="text-center text-2xl sm:text-3xl font-bold mb-10">
                CRIME REPORT
            </h1>

            {/* Form */}
            <MultiStepForm />
        </div>
    );
}