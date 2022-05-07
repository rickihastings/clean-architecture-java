package com.rickihastings.cleanarchitecture.utils;

import au.com.origin.snapshots.Expect;
import au.com.origin.snapshots.junit4.SnapshotClassRule;
import au.com.origin.snapshots.junit4.SnapshotRule;
import org.junit.ClassRule;
import org.junit.Rule;

public class UnitTestBase {

    @ClassRule
    public static SnapshotClassRule snapshotClassRule = new SnapshotClassRule();

    @Rule
    public SnapshotRule snapshotRule = new SnapshotRule(snapshotClassRule);
}
