import { NextResponse } from 'next/server';
import path from 'path';
import { promises as fs } from 'fs';

export async function GET() {
  try {
    // Read the mock data file
    const mockDataPath = path.join(process.cwd(), 'mock-api', 'regions.json');
    const fileContents = await fs.readFile(mockDataPath, 'utf8');
    const data = JSON.parse(fileContents);

    return NextResponse.json(data);
  } catch (error) {
    console.error('Failed to load mock region data:', error);
    return NextResponse.json({ error: 'Failed to load region data' }, { status: 500 });
  }
}
