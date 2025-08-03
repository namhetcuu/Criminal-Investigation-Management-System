'use client';

import { useParams } from 'next/navigation';

export default function SuspectsPage() {
  const { role } = useParams();

  return (
    <div className="p-6">
      <h1 className="text-2xl font-bold mb-4">Suspects - Role: {role}</h1>
      <table className="w-full border text-left">
        <thead className="bg-gray-100">
          <tr>
            <th className="p-2 border">Name</th>
            <th className="p-2 border">Crime</th>
            <th className="p-2 border">Status</th>
          </tr>
        </thead>
        <tbody>
          <tr className="hover:bg-gray-50">
            <td className="p-2 border">Tom Hardy</td>
            <td className="p-2 border">Burglary</td>
            <td className="p-2 border">Under Surveillance</td>
          </tr>
        </tbody>
      </table>
    </div>
  );
}